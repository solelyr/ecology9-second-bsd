package com.engine.erp.service.impl;

import com.engine.core.impl.Service;
import com.engine.erp.cmd.ExpenseToIncomeCmd;
import com.engine.erp.cmd.TravelToExpenseCmd;
import com.engine.erp.service.ErpService;
import com.engine.erp.vo.ErpResponse;
import com.solelyr.common.utils.EcologyRestUtil;
import weaver.soa.workflow.request.RequestInfo;

/**
 * @DESCRIPTION:
 * @USER: solelyr
 * @DATE: 2026/9/15 12:08
 */
public class ErpServiceImpl extends Service implements ErpService {

    @Override
    public Boolean ExpenseToIncome(RequestInfo requestInfo) {
        return commandExecutor.execute(new ExpenseToIncomeCmd(user,requestInfo));
    }

    @Override
    public Boolean TravelToExpense(RequestInfo requestInfo) {
        return commandExecutor.execute(new TravelToExpenseCmd(user,requestInfo));
    }
}
