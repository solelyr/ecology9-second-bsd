package com.engine.module.erp.workflow;

import com.solelyr.common.service.WorkflowAction;
import com.solelyr.common.utils.LoggerUtil;
import weaver.integration.logging.Logger;
import weaver.soa.workflow.request.RequestInfo;

/**
 * @DESCRIPTION:
 * @USER: solelyr
 * @DATE: 2026/8/20 9:27
 */
public class WorkflowActionTest0001 extends WorkflowAction {
    Logger log = LoggerUtil.getLogger();


    @Override
    protected Boolean doExecute(RequestInfo requestInfo) {
        return true;
    }
}
