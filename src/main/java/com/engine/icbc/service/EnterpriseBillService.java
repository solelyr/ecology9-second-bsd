package com.engine.icbc.service;

/**
 * @DESCRIPTION: 工商银行票据接口
 * @USER: solelyr
 * @DATE: 2026/9/18 11:38
 */
public interface EnterpriseBillService {
    /**
     * 新一代票据持有信息查询
     *
     * @return
     */
    Boolean QstackBillSync(String dueDateBgn, String dueDateEnd);
}
