package com.engine.erp.cmd;

import com.engine.core.interceptor.CommandContext;
import com.engine.erp.vo.ErpResponse;
import com.solelyr.common.service.WorkflowCommand;
import com.solelyr.common.utils.EcologyRestUtil;
import com.solelyr.common.utils.WorkflowUtil;
import weaver.hrm.User;
import weaver.soa.workflow.request.RequestInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @DESCRIPTION: 差旅报销推送ERP收支单
 * @USER: solelyr
 * @DATE: 2026/9/15 20:30
 */
public class TravelToExpenseCmd extends WorkflowCommand<EcologyRestUtil> {

    public TravelToExpenseCmd(User user, RequestInfo requestInfo) {
        this.user = user;
        this.requestInfo = requestInfo;
    }

    @Override
    public EcologyRestUtil execute(CommandContext commandContext) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        Map<String, Object> mainData = WorkflowUtil.getMainData(requestInfo);



        return null;
    }
}
