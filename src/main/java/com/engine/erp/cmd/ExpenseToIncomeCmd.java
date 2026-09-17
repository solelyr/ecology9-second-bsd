package com.engine.erp.cmd;

import com.engine.core.exception.ECException;
import com.engine.core.interceptor.CommandContext;
import com.engine.erp.entity.InComeAndExpensesEntity;
import com.engine.erp.enums.ErpConfig;
import com.engine.erp.util.ErpHttpHelp;
import com.engine.erp.vo.ErpResponse;
import com.icbc.api.internal.util.internal.util.fastjson.JSON;
import com.solelyr.common.service.WorkflowCommand;
import com.solelyr.common.utils.EcologyRestUtil;
import com.solelyr.common.utils.WorkflowUtil;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.hrm.User;
import weaver.hrm.passwordprotection.dao.HrmResourceDao;
import weaver.hrm.passwordprotection.domain.HrmResource;
import weaver.soa.workflow.request.RequestInfo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @DESCRIPTION: 费用报销推送ERP收支单
 * @USER: solelyr
 * @DATE: 2026/9/15 20:30
 */
public class ExpenseToIncomeCmd extends WorkflowCommand<Boolean> {

    public ExpenseToIncomeCmd(User user, RequestInfo requestInfo) {
        this.user = user;
        this.requestInfo = requestInfo;
    }

    @Override
    public Boolean execute(CommandContext commandContext) {
        String requestId = requestInfo.getRequestid();
        log.info("#TravelToExpenseCmd 费用报销推送ERP收支单 ====== " + requestId);
        Map<String, Object> mainData = WorkflowUtil.getMainData(requestInfo);
        InComeAndExpensesEntity income = getInComeAndExpensesEntity(mainData);
        String result = ErpHttpHelp.post(requestInfo.getRequestid(), ErpConfig.INCOMEANDEXPENSES,income);
        log.info("#TravelToExpenseCmd 费用报销推送ERP收支单 ====== " + requestId + " 返回结果：" + result);
        ErpResponse response = JSON.parseObject(result,ErpResponse.class);
        if (response.isSuccess()){
            String doc_no = Util.null2String(response.getStd_data().getParameter().getResult().getSuccess().get(0).getDoc_no());
            if(!update(doc_no)) throw new ECException("费用报销推送完成更新主表ERP收支单单号出错");
        }
        return true;
    }

    private InComeAndExpensesEntity getInComeAndExpensesEntity(Map<String, Object> mainData) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());

        String userId = Util.null2String(mainData.get("sqr"));
        Map<String,Comparable> userParams = new HashMap<>();
        userParams.put("userId", userId);
        List<HrmResource> hrmList = new HrmResourceDao().find(userParams);
        HrmResource applyUser = !hrmList.isEmpty() ? hrmList.get(0) : null;
        InComeAndExpensesEntity inCome = InComeAndExpensesEntity.builder()
                .om_company_id(Util.null2String(mainData.get("gs")))
                .doc_type_no("K307")
                .doc_date(date)
                .bookkeeping_date(date)
                .payment_type("-1")
                .settlement_object_type("3")
                .settlement_object_no(applyUser != null ? Util.null2String(applyUser.getWorkcode()) : "")
                .payment_property("3")
                .settlement_method_no(Util.null2String(mainData.get("jsfs")))
                .bank_account_no(Util.null2String(mainData.get("fkzh")))
                .exchange_rate("1")
                .trans_curr_amount(new BigDecimal(Util.null2String(mainData.get("sjdkje"))))
                .require_system_verify_flag(true)
                .build();

        return inCome;
    }

    private boolean update(String doc_no){
        String tableName = requestInfo.getRequestManager().getBillTableName();
        String requestId = requestInfo.getRequestid();
        String sql = "update " + tableName + " set erpszdh = '" + doc_no + "' where requestid = '" + requestId + "'";
        RecordSet rs = new RecordSet();
        return rs.executeUpdate(sql);
    }
}
