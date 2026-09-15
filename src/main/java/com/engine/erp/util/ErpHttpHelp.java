package com.engine.erp.util;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.engine.erp.entity.HttpLogEntity;
import com.engine.erp.enums.ErpConfig;
import com.solelyr.common.utils.LoggerUtil;
import weaver.integration.logging.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @DESCRIPTION: erp的请求工具类
 * @USER: solelyr
 * @DATE: 2026/09/15 09:54:15
 */
public class ErpHttpHelp {
    static Logger log = LoggerUtil.getLogger(ErpHttpHelp.class);

    public static String post(String requestId, ErpConfig erpConfig, Object data){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String time = sdf.format(new Date());
        String digi_type = "sync";
        String digi_protocol = "raw";
        String digi_data_exchange_protocol = "1.0";
        String ip = "172.16.0.252";
        String digi_host = "{\"ver\":\"5.7\",\"prod\":\"" + erpConfig.getProd() + "\",\"timezone\":\"+8\",\"ip\":\"" + ip + "\",\"id\":\"\",\"lang\":\"zh_CN\",\"acct\":\"dcms\",\"timestamp\":\"" + time + "\"}";
        String digi_service = "{\"prod\":\"E10\",\"ip\":\"" + ip + "\",\"name\":\"" + erpConfig.getName() + "\",\"id\":\"" + erpConfig.getId() + "\"}";
        String all = digi_host + digi_service;
        String digi_key = stringToMD5(all);
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("digi_type", digi_type);
        headersMap.put("digi_protocol", digi_protocol);
        headersMap.put("digi_host", digi_host);
        headersMap.put("digi_service", digi_service);
        headersMap.put("digi_key", digi_key);
        headersMap.put("digi_data_exchange_protocol", digi_data_exchange_protocol);
        return post(requestId,headersMap,data);
    }

    /**
     * POST请求发送
     * @param requestId 流程ID，对应UUID
     * @param data REQ数据体
     * @return
     */
    public static String post(String requestId,Map<String,String> header, Object data){
        log.info("-------sap统一调用接口日志--start------");

        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 记录接口开始时间
        String startTime = LocalDateTime.now().format(formatter);
        long startMillis = System.currentTimeMillis();
        String uuid = UUID.randomUUID().toString();
        Map<String,Object> params = new HashMap<>();
        params.put("data",data);
        String result = "";
        String path = "http://172.16.0.252:9990/CROSS/RESTful";
        try {
            result = HttpRequest.post(path)
                    .headerMap(header,true)
                    .body(JSONObject.toJSONString(params))
                    .execute().body();

            log.info("请求header："+JSONObject.toJSONString(header));
            log.info("请求参数："+JSONObject.toJSONString(params));
            log.info("返回结果："+result);
        }catch (Exception e){
            log.error("erp请求工具类发生异常！:"+e.getMessage());
        }finally {
            // 计算总耗时（毫秒）
            long durationMillis = System.currentTimeMillis() - startMillis;
            String endTime = LocalDateTime.now().format(formatter);
            HttpLogEntity.saveLog(HttpLogEntity.builder()
                    .qqrc(JSONObject.toJSONString(params))
                    .qqcc(result)
                    .qqkssj(startTime)
                    .qqjssj(endTime)
                    .qqhshm(durationMillis)
                    .lcid(requestId)
                    .uuid(uuid)
                    .qqdz(path)
                    .build());
        }

        log.info("-------erp统一调用接口日志--end------");
        return result;
    }

    //md5加密32位大写
    private static String stringToMD5(String plainText) {
        byte[] mdBytes = null;
        try {
            mdBytes = MessageDigest.getInstance("MD5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5算法不存在！");
        }
        String mdCode = new BigInteger(1, mdBytes).toString(16);

        if (mdCode.length() < 32) {
            int a = 32 - mdCode.length();
            for (int i = 0; i < a; i++) {
                mdCode = "0" + mdCode;
            }
        }
        return mdCode.toUpperCase(); //返回32位大写
    }
}
