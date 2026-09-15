package com.engine.erp.workflow;

import com.solelyr.common.utils.LoggerUtil;
import weaver.integration.logging.Logger;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * @DESCRIPTION:
 * @USER: solelyr
 * @DATE: 2026/8/20 9:27
 */
public class WorkflowActionTest0001 implements Action {
    Logger log = LoggerUtil.getLogger();

    @Override
    public String execute(RequestInfo requestInfo) {
        log.info("WorkflowActionTest0001");
        return Action.SUCCESS;
    }
}
