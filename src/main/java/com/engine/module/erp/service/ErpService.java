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
     * @param requestInfo
     * @return
     */
    Boolean ExpenseToIncome(RequestInfo requestInfo);

    /**
     * 差旅报销推送收支单接口
     * @param requestInfo
     * @return
     */
    Boolean TravelToExpense(RequestInfo requestInfo);

    /**
     * 票据确认流程推送ERP创建应收票据
     * @param requestInfo
     * @return
     */
    Boolean ReceivableNoteCreate(RequestInfo requestInfo);
}
