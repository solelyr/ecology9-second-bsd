package com.engine.icbc.entity;

import cn.hutool.core.bean.BeanUtil;
import com.icbc.api.response.MybankEnterpriseBillQstockbillResponseV1;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @DESCRIPTION:
 * @USER: solelyr
 * @DATE: 2026/9/18 11:12
 */

public class QstockbillResponseRdV1sup
        extends MybankEnterpriseBillQstockbillResponseV1
        .MybankEnterpriseBillQstockbillResponseRdV1 {

    public static QstockbillResponseRdV1sup from(
            MybankEnterpriseBillQstockbillResponseV1
                    .MybankEnterpriseBillQstockbillResponseRdV1 source) {

        QstockbillResponseRdV1sup target =
                new QstockbillResponseRdV1sup();

        // 将父类中的全部原始字段复制到扩展对象
        BeanUtil.copyProperties(source, target);
        return target;
    }

    /**
     * 票据持有日期
     */
    public String getPjcyrq() {
        return formatDate(getHoldDate());
    }

    /**
     * 出票日期
     */
    public String getCprq() {
        return formatDate(getIssueDate());
    }

    /**
     * 票据金额：分转换为元
     */
    public BigDecimal getPjbjey() {
        Long amount = getRangeAmt();
        return amount == null
                ? null
                : BigDecimal.valueOf(amount, 2);
    }

    private static String formatDate(String date) {
        if (date == null || date.isEmpty()) {
            return date;
        }
        try {
            return LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE)
                    .format(DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return date;
        }
    }
}
