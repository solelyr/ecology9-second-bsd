package com.engine.module.erp.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @DESCRIPTION: 收支单接口返回的数据
 * @USER: solelyr
 * @DATE: 2026/9/20 22:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class IncomeAndExpensesVo {
    String doc_no;
}
