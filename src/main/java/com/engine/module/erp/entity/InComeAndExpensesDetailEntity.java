package com.engine.module.erp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.math.BigDecimal;

/**
 * @DESCRIPTION: ERP收支单实体对象明细表
 * @USER: solelyr
 * @DATE: 2026/9/15 20:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class InComeAndExpensesDetailEntity {
    /** 申请序号 */
    private Integer application_seq;

    /** 收付项目编号 */
    private String payment_project_no;

    /** 项目编号 */
    private String project_no;

    /** 归属部门编号 */
    private String attribution_department_no;

    /** 归属人员编号 */
    private String attribution_employee_no;

    /** 税务发票类型 */
    private String tax_invoice_type;

    /** 税务发票号码 */
    private String tax_invoice_no;

    /** 税种编号 */
    private String tax_no;

    /** 含税标识 */
    private String is_tax_included;

    /** 价税合计(原币) */
    private BigDecimal total_trans_curr_tax;

    /** 税额(原币) */
    private BigDecimal trans_curr_tax_amount;

    /** 价税合计(本币) */
    private BigDecimal total_local_curr_tax;

    /** 税额(本币) */
    private BigDecimal local_curr_tax_amount;

    /** 备注 */
    private String remark;
}
