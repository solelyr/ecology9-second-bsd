package com.engine.erp.service;

import com.engine.erp.vo.ErpResponse;
import com.solelyr.common.utils.EcologyRestUtil;
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
    EcologyRestUtil ExpenseToIncome(RequestInfo requestInfo);

    /**
     * 差旅报销推送收支单接口
     */
    EcologyRestUtil TravelToExpense(RequestInfo requestInfo);
}
