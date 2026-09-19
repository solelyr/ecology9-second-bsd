package com.engine.icbc.demo;

import com.icbc.api.DefaultIcbcClient;
import com.icbc.api.request.MybankEnterpriseBillQstockbillRequestV1;
import com.icbc.api.response.MybankEnterpriseBillQstockbillResponseV1;
import weaver.file.Prop;
import weaver.general.Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

/**
 * @DESCRIPTION:
 * @USER: solelyr
 * @DATE: 2026/9/15 21:23
 */
public class MybankEnterpriseBillQstockbillTest {

    public static void main(String[] args) throws Exception {
        final String CONFIG_RESOURCE = "/prop/SOLELYR_QSTOCK_BILL.properties";
        final Properties CONFIG = Prop.loadTemplateProp(CONFIG_RESOURCE);

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
//    final String CERTIFICATE_RESOURCE = Util.null2String(CONFIG.getProperty(("icbc.certificate-resource")));
        final String SERVICE_PATH = Util.null2String(CONFIG.getProperty(("icbc.service-path")));
        final String CERTIFICATE_RESOURCE = Util.null2String(CONFIG.getProperty(("icbc.sm")));

        String icbcCa = CERTIFICATE_RESOURCE;
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
        client.setIcbc_ca(icbcCa);
//        client.setIcbcCaConsistentChkFlag(true);
        try {
            MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestBizV1 bizContent =
                    new MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestBizV1();
            MybankEnterpriseBillQstockbillRequestV1 request = new MybankEnterpriseBillQstockbillRequestV1();
            // 请对照接口文档用bizContent.setxxx()方法对业务上送数据进行赋值
            bizContent.setTransCode("QSTOCKBILL"); // 交易代码
            bizContent.setTranDate("20260918"); // 交易日期：ERP系统产生，格式是yyyyMMdd
            bizContent.setTranTime("09430000"); // 交易时间：ERP系统产生，格式如HHmmssSSS，精确到毫秒
            bizContent.setLanguage("zh_CN"); // 语言：zh_CN简体中文，en_US英文，默认zh_CN
            bizContent.setfSeqNo("QSTOCKBILL1022393036"); // 指令包序列号：ERP系统产生，一个集团永远不能重复
//            bizContent.setHolderAcctId("0200003309004709145"); // 持票人账号
//            bizContent.setPackAmtBgn(0L); // 票据（包）金额起（指票面金额）：单位:分
//            bizContent.setPackAmtEnd(100000000000L); // 票据（包）金额止（指票面金额）：单位:分
            bizContent.setCdTp("AC01"); // 票据种类：AC01银承,AC02商承,0为查询全部
            bizContent.setDueDateBgn("20260918"); // 票据到期日开始：格式为yyyyMMdd，到期日开始和结束之间不超过一年
            bizContent.setDueDateEnd("20270918"); // 票据到期日结束：格式为yyyyMMdd，到期日开始和结束之间不超过一年
//            bizContent.setHoldDateBgn("20260101"); // 票据持有开始日期：格式为yyyyMMdd
//            bizContent.setHoldDateEnd("20260901"); // 票据持有结束日期：格式为yyyyMMdd
//            bizContent.setIssueDateBgn("20220106"); // 出票开始日期：格式为yyyyMMdd
//            bizContent.setIssueDateEnd("20220506"); // 出票结束日期：格式为yyyyMMdd
//            bizContent.setAccptrName("中国工商银行股份有限公司杭州支行"); // 承兑人名称
//            bizContent.setDrwrName("杭州染料有限公司"); // 出票人名称
//            bizContent.setPreName("杭州生物科技有限公司"); // 前手背书人名称
            bizContent.setNextTag("2"); // 查询下一页标识：供客户翻页时上送的字段
            List<MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestRdV1> rdList = new ArrayList<>(); // 循环区：最多支持100个
            MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestRdV1 rd = new MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestRdV1();
            rd.setPackNo("110233520800020100421000113497"); // 票据包号：每笔票据号码长度为30；
//            rdList.add(rd);
            bizContent.setRd(rdList);
            request.setServiceUrl(BASE_URL + "/" + SERVICE_PATH);
            request.setBizContent(bizContent);
            MybankEnterpriseBillQstockbillResponseV1 response = client.execute(request);
            if (response.isSuccess()) {
                // 业务成功处理
                System.out.println("success");
            } else {
                // 失败
                System.out.println("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String loadCertificate(String resourcePath) throws IOException {
        try (InputStream inputStream =
                     MybankEnterpriseBillQstockbillTest.class.getResourceAsStream(resourcePath)) {

            if (inputStream == null) {
                throw new IOException("找不到证书资源: " + resourcePath);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int length;

            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

            byte[] certificateBytes = outputStream.toByteArray();
            String certificateText =
                    new String(certificateBytes, StandardCharsets.US_ASCII).trim();

            // PEM 格式：只返回 PEM 中间的 Base64 内容
            if (certificateText.contains("-----BEGIN CERTIFICATE-----")) {
                return certificateText
                        .replace("-----BEGIN CERTIFICATE-----", "")
                        .replace("-----END CERTIFICATE-----", "")
                        .replaceAll("\\s+", "");
            }

            // DER 格式：将二进制内容编码成 Base64
            return Base64.getEncoder().encodeToString(certificateBytes);
        }
    }
}
