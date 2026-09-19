package com.engine.erp.entity;

import com.engine.erp.enums.ErpPk;
import com.engine.util.EcologyRestUtilImpl;
import com.solelyr.common.entity.EcologyRestEntity;
import com.solelyr.common.utils.EcologyRestUtil;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @DESCRIPTION: http请求相关的日志信息
 * @USER: solelyr
 * @DATE: 2025/12/16 09:26:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class HttpLogEntity {
    /** 请求地址 qqdz */
    private String qqdz;

    /** token */
    private String token;

    /** 流程ID lcid */
    private String lcid;

    /** UUID uuid */
    private String uuid;

    /** 接口ID jkid */
    private String jkid;

    /** 接口描述 jkms */
    private String jkms;

    /** 接口编号 jkbh */
    private String jkbh;

    /** 请求入参 qqrc */
    private String qqrc;

    /** 请求出参 qqcc */
    private String qqcc;

    /** 请求开始时间 qqkssj */
    private String qqkssj;

    /** 请求结束时间 qqjssj */
    private String qqjssj;

    /** 请求耗时（毫秒） qqhshm */
    private Long qqhshm;

    /** 是否本地请求 0表示是，1表示否 */
    private Integer sfbdqq;

    public static Map<String,Object> saveLog(HttpLogEntity entity){
        EcologyRestUtil restUtil = new EcologyRestUtilImpl();
       return restUtil.saveOrUpdate(ErpPk.HTTPLOG,new EcologyRestEntity().setMainTable(entity));
    }
}
