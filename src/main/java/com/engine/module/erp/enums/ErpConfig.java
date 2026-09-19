package com.engine.module.erp.enums;

/**
 * @DESCRIPTION: erp接口配置
 * @USER: solelyr
 * @DATE: 2026/9/15 11:52
 */
public enum ErpConfig {
    INCOMEANDEXPENSES("Beisit_External","e10.oapi.expense.receipt.doc.data.create","YOPENAPI","收支单"),

    ;

    private final String id;
    private final String name;
    private final String prod;
    private final String description;

    ErpConfig(String id, String name, String prod, String description) {
        this.id = id;
        this.name = name;
        this.prod = prod;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProd() {
        return prod;
    }

    public String getDescription() {
        return description;
    }
}
