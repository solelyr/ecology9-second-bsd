package com.engine.module.erp.workflow;

import com.engine.util.BsdServiceUtil;
import com.solelyr.common.service.WorkflowAction;
import weaver.soa.workflow.request.RequestInfo;

/**
 * @DESCRIPTION: 差旅报销推送ERP收支单 流程动作
 * @USER: solelyr
 * @DATE: 2026/9/19 22:39
 */
public class TravelToExpenseAction extends WorkflowAction {
    @Override
    protected Boolean doExecute(RequestInfo requestInfo) {
        return BsdServiceUtil.getErpService().TravelToExpense(requestInfo);
    }
}
