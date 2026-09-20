package com.engine.module.erp.cmd;

import com.engine.core.exception.ECException;
import com.engine.core.interceptor.CommandContext;
import com.engine.module.erp.entity.ReceivableNoteEntity;
import com.engine.module.erp.enums.ErpConfig;
import com.engine.module.erp.util.ErpHttpHelp;
import com.engine.module.erp.util.ErpResponseUtil;
import com.engine.module.erp.vo.ErpResponse;
import com.engine.module.erp.vo.IncomeAndExpensesVo;
import com.engine.module.erp.vo.ReceivableNoteVo;
import com.solelyr.common.service.WorkflowCommand;
import com.solelyr.common.utils.WorkflowUtil;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.soa.workflow.request.RequestInfo;
import weaver.hrm.User;

import java.util.Map;

/**
 * @DESCRIPTION:
 * @USER: solelyr
 * @DATE: 2026/9/20 20:13
 */
public class ReceivableNoteCreateCmd extends WorkflowCommand<Boolean> {
    public ReceivableNoteCreateCmd(User user, RequestInfo requestInfo) {
        this.user = user;
        this.requestInfo = requestInfo;
    }

    @Override
    public Boolean execute(CommandContext commandContext) {
        String requestId = requestInfo.getRequestid();
        log.info("#ReceivableNoteCreateCmd 票据确认流程推送ERP创建应收票据接口 ====== " + requestId);
        Map<String, Object> mainData = WorkflowUtil.getMainData(requestInfo);
        ReceivableNoteEntity receivableNote = getInComeAndExpensesEntity(mainData);
        String result = ErpHttpHelp.post(requestInfo.getRequestid(), ErpConfig.RECEIVABLENOTECREATE,receivableNote);
        log.info("#ReceivableNoteCreateCmd 票据确认流程推送ERP创建应收票据接口 ====== " + requestId + " 返回结果：" + result);
        ErpResponse<ReceivableNoteVo> response = ErpResponseUtil.parse(result, ReceivableNoteVo.class);
        if (response.isSuccess()){
            String note_no = Util.null2String(response.getStd_data().getParameter().getResult().getSuccess().get(0).getNote_no());
            if(!update(note_no)) throw new ECException("差旅报销推送完成更新主表ERP收支单单号出错");
        }

        return true;
    }

    private ReceivableNoteEntity getInComeAndExpensesEntity(Map<String,Object> mainData){
        String om_company_id = Util.null2String(mainData.get("om_company_id"));
        String cdTp = Util.null2String(mainData.get("cdTp")); // 票据类型
        String settlement_method_no = "";
        if("AC01".equals(cdTp)) settlement_method_no = "8"; // 银承
        if("AC02".equals(cdTp)) settlement_method_no = "9"; // 商承
        String note_pkg_no = Util.null2String(mainData.get("packNo")); // 票据包号码
        String trans_date = Util.null2String(mainData.get("sprq")); // 收票日期
        String note_range_start_seqno = Util.null2String(mainData.get("rangeBgn")); // 起始序号
        String bookkeeping_date = Util.null2String(mainData.get("pjcyrq")); // 记账日期（持有日期）
        String settlement_object_no = Util.null2String(mainData.get("sqr")); // 结算对象编号（申请人）
        String settlement_object_name = Util.null2String(mainData.get("preName")); // 结算对象名称（前手背书人名称）
        String issue_date = Util.null2String(mainData.get("cprq")); // 出票日期
        String due_date = Util.null2String(mainData.get("pjdqr")); // 到期日（票据到期日）
        String face_amt_tc = Util.null2String(mainData.get("pjbjey")); // 票面原币金额（票据（包）金额（元））
        String acceptor = "AC01".equals(cdTp) ? "" : Util.null2String(mainData.get("accptrName")); // 银行为空，商业为:承兑人（承兑行/人名称）
        String drawer = Util.null2String(mainData.get("drwrName")); // 出票人（出票人全称）
        String paying_bank_fullname = "AC02".equals(cdTp) ? "" : Util.null2String(mainData.get("accptrName")); // 商业为空，银行为:付款行全称（承兑行/人名称）

        // 默认值
        String settlement_object_type = "1"; // 结算对象类型
        String face_currency_no = "CNY"; // 票面货币
        String exchange_rate = "1"; // 汇率
        String payment_currency_no = "CNY"; // 兑付货币
        String collecting_bank_account_no = "1202054519900024045"; // 收款行账号
        String responsibility_org_no = "1"; // 责任域编号
        Boolean accounts_sys_flag = true; // 需往来系统核销

        return ReceivableNoteEntity.builder()
                .om_company_id(om_company_id)
                .settlement_method_no(settlement_method_no)
                .note_pkg_no(note_pkg_no)
                .trans_date(trans_date)
                .note_range_start_seqno(note_range_start_seqno)
                .bookkeeping_date(bookkeeping_date)
                .settlement_object_type(settlement_object_type)
                .settlement_object_no(settlement_object_no)
                .settlement_object_name(settlement_object_name)
                .issue_date(issue_date)
                .due_date(due_date)
                .face_currency_no(face_currency_no)
                .exchange_rate(exchange_rate)
                .face_amt_tc(face_amt_tc)
                .acceptor(acceptor) //
                .drawer(drawer)
                .paying_bank_fullname(paying_bank_fullname)
                .payment_currency_no(payment_currency_no)
                .collecting_bank_account_no(collecting_bank_account_no)
                .responsibility_org_no(responsibility_org_no)
                .accounts_sys_flag(accounts_sys_flag)
                .build();
    }

    /**
     * 更新接口返回的票据号码
     * @param note_no
     * @return
     */
    private Boolean update(String note_no){
        String tableName = requestInfo.getRequestManager().getBillTableName();
        String requestId = requestInfo.getRequestid();
        String sql = "UPDATE " + tableName + " SET note_no = ? where requestId = ?";
        RecordSet rs = new RecordSet();
        return rs.executeUpdate(sql,note_no,requestId);
    }
}
