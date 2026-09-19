package com.engine.module.erp.service;

import weaver.soa.workflow.request.RequestInfo;

/**
 * @DESCRIPTION:
 * @USER: solelyr
 * @DATE: 2026/9/15 12:08
 */
public interface ErpService {

    /**
     * 费用报销推送收支单接口
     */
    Boolean ExpenseToIncome(RequestInfo requestInfo);

    /**
     * 差旅报销推送收支单接口
     */
    Boolean TravelToExpense(RequestInfo requestInfo);
}
