package com.engine.module.icbc.enums;


import com.solelyr.common.service.EcologyRestPk;

/**
 * @DESCRIPTION: 通用的一些建模接口标识
 * @USER: solelyr
 * @DATE: 2025/12/16 09:32:01
 */
public enum IcbcPk implements EcologyRestPk {
    QSTACKBILL("JM_CYPJ","持有票据"),
    ;

    IcbcPk(String pk, String name){
        this.pk = pk;
        this.name = name;
    }

    private final String pk;
    private final String name;

    @Override
    public String getPK() {
        return this.pk;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
