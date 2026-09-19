package com.engine.module.erp.enums;


import com.solelyr.common.service.EcologyRestPk;

/**
 * @DESCRIPTION: 通用的一些建模接口标识
 * @USER: solelyr
 * @DATE: 2025/12/16 09:32:01
 */
public enum ErpPk implements EcologyRestPk {
    HTTPLOG("JM_QQRZ","http请求日志"),
    ACTION( "JM_SDYX","RZW_流程自定义接口手动执行"),
    SAPSUCCECCLOG("JM_CGRZ","SAP_接口成功的记录"),
    ;

    ErpPk(String pk, String name){
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
