package com.engine.module.erp.workflow;

import com.engine.util.BsdServiceUtil;
import com.solelyr.common.service.WorkflowAction;
import weaver.soa.workflow.request.RequestInfo;

/**
 * @DESCRIPTION: 票据确认流程推送ERP创建应收票据
 * @USER: solelyr
 * @DATE: 2026/9/20 22:50
 */
public class ReceivableNoteAction extends WorkflowAction {
    @Override
    protected Boolean doExecute(RequestInfo requestInfo) {
        return BsdServiceUtil.getErpService().ReceivableNoteCreate(requestInfo);
    }
}
