package com.engine.erp.entity;

import com.weaverboot.frame.dao.anno.Association;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @DESCRIPTION: ERP收支单实体对象
 * @USER: solelyr
 * @DATE: 2026/9/15 20:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class InComeAndExpensesEntity {
    /** 公司 */
    private String om_company_id;

    /** 单据类型 */
    private String doc_type_no;

    /** 单号 */
    private String doc_no;

    /** 业务日期 */
    private String doc_date;

    /** 记账日期 */
    private String bookkeeping_date;

    /** 期间 */
    private String period;

    /** 收付类型 */
    private String payment_type;

    /** 结算对象类型 */
    private String settlement_object_type;

    /** 结算对象 */
    private String settlement_object_no;

    /** 收付性质 */
    private String payment_property;

    /** 结算方式 */
    private String settlement_method_no;

    /** 银行账号 */
    private String bank_account_no;

    /** 汇率 */
    private BigDecimal exchange_rate;

    /** 原币金额 */
    private BigDecimal trans_curr_amount;

    /** 本币金额 */
    private BigDecimal amt_fc;

    /** 票据簿 */
    private String note_book_id;

    /** 顺序号 */
    private Integer serial_no;

    /** 结算号 */
    private String settlement_no;

    /** 受控付款申请管理 */
    private String prmc_flag;

    /** 受控员工借支管理 */
    private String eamc_flag;

    /** 需核销申请单据 */
    private String vrd_flag;

    /** 电子支付 */
    private String w054_epi_flag;

    /** 费控来源 */
    private String oa_source;

    /** 需往来系统核销 */
    private String require_system_verify_flags;

    /** ERP收支单实体对象明细数据 */
    private List<InComeAndExpensesDetailEntity> cp_doc_verified;
}
