package com.engine.erp.cmd;

import com.engine.core.interceptor.CommandContext;
import com.engine.erp.vo.ErpResponse;
import com.solelyr.common.service.WorkflowCommand;
import com.solelyr.common.utils.EcologyRestUtil;
import weaver.hrm.User;
import weaver.soa.workflow.request.RequestInfo;

/**
 * @DESCRIPTION: 费用报销推送ERP收支单
 * @USER: solelyr
 * @DATE: 2026/9/15 20:30
 */
public class ExpenseToIncomeCmd extends WorkflowCommand<EcologyRestUtil> {

    public ExpenseToIncomeCmd(User user, RequestInfo requestInfo) {
        this.user = user;
        this.requestInfo = requestInfo;
    }

    @Override
    public EcologyRestUtil execute(CommandContext commandContext) {

        return null;
    }
}
