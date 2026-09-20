package com.engine.module.erp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @DESCRIPTION: ERP 创建应收票据 对象
 * @USER: solelyr
 * @DATE: 2026/9/20 20:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class ReceivableNoteEntity {
    /** 公司编号 */
    private String om_company_id;

    /** 票据类型 */
    private String settlement_method_no;

    /** 票据号码 */
    private String note_no;

    /** 票据(包)号 */
    private String note_pkg_no;

    /** 收票日期 */
    private String trans_date;

    /** 起始序号 */
    private String note_range_start_seqno;

    /** 记账日期 */
    private String bookkeeping_date;

    /** 子票据 */
    private String sub_note_flag;

    /** 结算对象类型 */
    private String settlement_object_type;

    /** 结算对象编号 */
    private String settlement_object_no;

    /** 结算对象名称 */
    private String settlement_object_name;

    /** 出票日期 */
    private String issue_date;

    /** 到期日 */
    private String due_date;

    /** 票面货币 */
    private String face_currency_no;

    /** 汇率 */
    private String exchange_rate;

    /** 票面原币金额 */
    private String face_amt_tc;

    /** 承兑人 */
    private String acceptor;

    /** 承兑协议 */
    private String acceptance_agreement;

    /** 交易合同 */
    private String trans_contract;

    /** 出票人 */
    private String drawer;

    /** 付款行账号 */
    private String paying_bank_account_no;

    /** 付款行全称 */
    private String paying_bank_fullname;

    /** 付款行行号 */
    private String paying_bank_no;

    /** 付款行地址 */
    private String paying_bank_address;

    /** 兑付货币 */
    private String payment_currency_no;

    /** 收款行账号 */
    private String collecting_bank_account_no;

    /** 责任域类型 */
    private String responsibility_org_type;

    /** 责任域编号 */
    private String responsibility_org_no;

    /** 责任人员编号 */
    private String responsibility_employee_no;

    /** 责任部门编码 */
    private String responsibility_department_no;

    /** 需往来系统核销 */
    private Boolean accounts_sys_flag;

    /** 收付项目 */
    private String other_arap_item_code;

    /** 项目编号 */
    private String project_no;

    /** 备注 */
    private String remark;
}
