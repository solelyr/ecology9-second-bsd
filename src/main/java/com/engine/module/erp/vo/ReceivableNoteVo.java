package com.engine.module.erp.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @DESCRIPTION: ERP创建应收票据返回结果
 * @USER: solelyr
 * @DATE: 2026/9/20 22:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class ReceivableNoteVo {
    /**
     * 公司编号
     */
    private String om_company_id;

    /**
     * 票据类型
     */
    private String settlement_method_no;

    /**
     * 票据号码
     */
    private String note_no;
}
