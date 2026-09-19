package com.engine.module.icbc.cmd;

import com.alibaba.fastjson.JSONObject;
import com.engine.core.exception.ECException;
import com.engine.core.interceptor.AbstractCommand;
import com.engine.core.interceptor.CommandContext;
import com.engine.module.icbc.entity.QstockbillResponseRdV1sup;
import com.engine.module.icbc.enums.IcbcPk;
import com.engine.util.HttpLogUtil;
import com.engine.util.EcologyRestUtilImpl;
import com.solelyr.common.utils.EcologyRestUtil;
import weaver.file.Prop;
import com.icbc.api.DefaultIcbcClient;
import com.icbc.api.request.MybankEnterpriseBillQstockbillRequestV1;
import com.icbc.api.response.MybankEnterpriseBillQstockbillResponseV1;
import com.solelyr.common.entity.EcologyRestEntity;
import com.solelyr.common.utils.LoggerUtil;
import weaver.general.Util;
import weaver.integration.logging.Logger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @DESCRIPTION:
 * @USER: solelyr
 * @DATE: 2026/9/18 11:42
 */
public class QstackBillSyncCmd extends AbstractCommand<Boolean> {
    private String dueDateBgn; // 票据到期日开始
    private String dueDateEnd; // 票据到期日结束

    private static final String CONFIG_RESOURCE = "solelyr_bsd_icbc_bill";
    private final Logger log = LoggerUtil.getLogger(QstackBillSyncCmd.class);

    public QstackBillSyncCmd(String dueDateBgn, String dueDateEnd) {
        this.dueDateBgn = dueDateBgn;
        this.dueDateEnd = dueDateEnd;
    }

    @Override
    public Boolean execute(CommandContext commandContext) {
        log.info("####调用工行票据查询接口开始！####"+dueDateBgn+"---"+dueDateEnd);
        String nextTag = "-1"; // 初始页
        while (!"".equals(nextTag)){
            MybankEnterpriseBillQstockbillResponseV1 response = query(nextTag);
            log.info("####调用工行票据查询接口查询结果！####输入nextTag："+nextTag+"，接口返回结果："+response.isSuccess()+"，接口返回行数："+response.getTotalNum()+"，接口返回nextTag："+response.getNextTag());
            if (response.isSuccess()) {
                // 业务成功处理
                nextTag = response.getNextTag(); // 优先设置循环标示，避免中间异常
                List<MybankEnterpriseBillQstockbillResponseV1.MybankEnterpriseBillQstockbillResponseRdV1> rdV1List = response.getRd();
                List<com.engine.module.icbc.entity.QstockbillResponseRdV1sup> extendedList =
                        rdV1List.stream()
                                .map(QstockbillResponseRdV1sup::from)
                                .collect(Collectors.toList());

                List<EcologyRestEntity> ecologyRestList =
                        EcologyRestEntity.list2EcRestData(extendedList);

                EcologyRestUtil restUtil = new EcologyRestUtilImpl();
                Map<String,Object> resultMap = restUtil.saveOrUpdate(IcbcPk.QSTACKBILL, ecologyRestList);

                log.info("####调用工行票据查询接口写入建模数据结果！本次写数量："+ecologyRestList.size()+"，建模接口返回结果：" + ("1".equals(Util.null2String(resultMap.get("status"))) ? "成功！" : "失败！"));
            } else {
                // 失败
                log.info("####调用工行票据查询接口工行返回失败！####" + response.getReturnMsg());
                throw new ECException("####调用工行票据查询接口工行返回失败！####" + response.getReturnMsg());
            }
        }

        return true;
    }

    private MybankEnterpriseBillQstockbillResponseV1 query(String nextTag){

        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        // 记录接口开始时间
        String startTime = now.format(formatter);
        long startMillis = System.currentTimeMillis();

        // 格式化为字符串
        String nowFormat = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String timeFormat = now.format(DateTimeFormatter.ofPattern("HHmmssSSS"));
        String uuid = UUID.randomUUID().toString().replaceAll("-","");

        final Properties CONFIG = Prop.loadTemplateProp(CONFIG_RESOURCE);
        if (CONFIG == null) {
            throw new ECException("未找到工行票据配置文件：" + CONFIG_RESOURCE + ".properties");
        }
        final String APP_ID = Util.null2String(CONFIG.getProperty(("icbc.app-id")));
        final String APIGW_PUBLIC_KEY = Util.null2String(CONFIG.getProperty(("icbc.apigw-public-key")));
        final String PRI_KEY = Util.null2String(CONFIG.getProperty(("icbc.private-key")));
        final String BASE_URL = Util.null2String(CONFIG.getProperty(("icbc.base-url")));
        final String CA_PRIVATE_STR = Util.null2String(CONFIG.getProperty(("icbc.ca-private-key")));
        final String CA_PUBLIC_STR = Util.null2String(CONFIG.getProperty(("icbc.ca-public-key")));
        final String CA_PASSWORD = Util.null2String(CONFIG.getProperty(("icbc.ca-password")));
        final String SIGN_TYPE = Util.null2String(CONFIG.getProperty(("icbc.sign-type")));
        final String CHARSET = Util.null2String(CONFIG.getProperty(("icbc.charset")));
        final String FORMAT = Util.null2String(CONFIG.getProperty(("icbc.format")));
        final String SERVICE_PATH = Util.null2String(CONFIG.getProperty(("icbc.service-path")));
        final String CERTIFICATE_RESOURCE = Util.null2String(CONFIG.getProperty(("icbc.sm")));

        MybankEnterpriseBillQstockbillResponseV1 response = null;
        MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestBizV1 bizContent =
                new MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestBizV1();
        try {
            DefaultIcbcClient client = new DefaultIcbcClient(
                    APP_ID,
                    SIGN_TYPE,
                    CA_PRIVATE_STR,
                    CHARSET,
                    FORMAT,
                    APIGW_PUBLIC_KEY,
                    null,
                    null,
                    CA_PUBLIC_STR,
                    CA_PASSWORD
            );
            client.setIcbc_ca(CERTIFICATE_RESOURCE);
            MybankEnterpriseBillQstockbillRequestV1 request = new MybankEnterpriseBillQstockbillRequestV1();
            // 请对照接口文档用bizContent.setxxx()方法对业务上送数据进行赋值
            bizContent.setTransCode("QSTOCKBILL"); // 交易代码
            bizContent.setTranDate(nowFormat); // 交易日期：ERP系统产生，格式是yyyyMMdd
            bizContent.setTranTime(timeFormat); // 交易时间：ERP系统产生，格式如HHmmssSSS，精确到毫秒
            bizContent.setLanguage("zh_CN"); // 语言：zh_CN简体中文，en_US英文，默认zh_CN
            bizContent.setfSeqNo(uuid); // 指令包序列号：ERP系统产生，一个集团永远不能重复
            bizContent.setCdTp("0"); // 票据种类：AC01银承,AC02商承,0为查询全部
            bizContent.setDueDateBgn(dueDateBgn.replaceAll("-","")); // 票据到期日开始：格式为yyyyMMdd，到期日开始和结束之间不超过一年
            bizContent.setDueDateEnd(dueDateEnd.replaceAll("-","")); // 票据到期日结束：格式为yyyyMMdd，到期日开始和结束之间不超过一年
            bizContent.setNextTag("-1".equals(nextTag) ? "" : Util.null2String(nextTag)); // 查询下一页标识：供客户翻页时上送的字段。默认为空 表示第一页，后续以接口返回的为准

            bizContent.setRd(new ArrayList<>());// 循环区：最多支持100个
            request.setServiceUrl(BASE_URL + "/" + SERVICE_PATH);
            request.setBizContent(bizContent);
            response = client.execute(request);
            return response;
        }catch (Exception e){
            e.printStackTrace();
            log.info("####调用工行票据查询接口发生异常！####" + e.getMessage());
            throw new ECException("####调用工行票据查询接口发生异常！####" + e.getMessage() );
        }finally {
            long durationMillis = System.currentTimeMillis() - startMillis;
            String endTime = LocalDateTime.now().format(formatter);
            HttpLogUtil.saveLog(HttpLogUtil.builder()
                .lcid("")
                .uuid(uuid)
                .jkid("qstockbill")
                .jkms("新一代票据持有信息查询")
                .qqrc(JSONObject.toJSONString(bizContent))
                .qqcc((response != null ? response.getReturnMsg() : "请求异常，未返回结果！") + (response.getRd() != null ? "\n接口查询数量：" + response.getRd().size() : ""))
                .qqkssj(startTime)
                .qqjssj(endTime)
                .qqhshm(durationMillis)
                .qqdz(BASE_URL)
                .build());
        }
    }
}
