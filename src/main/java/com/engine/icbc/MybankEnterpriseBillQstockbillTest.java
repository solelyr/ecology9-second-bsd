package com.engine.icbc;

import com.icbc.api.DefaultIcbcClient;
import com.icbc.api.internal.util.internal.util.fastjson.JSON;
import com.icbc.api.request.MybankEnterpriseBillQstockbillRequestV1;
import com.icbc.api.response.MybankEnterpriseBillQstockbillResponseV1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @DESCRIPTION:
 * @USER: solelyr
 * @DATE: 2026/9/15 21:23
 */
public class MybankEnterpriseBillQstockbillTest {

    protected static String APP_ID = "11000000000000073859";
//    protected static String APIGW_PUBLIC_KEY = "##############start public key##############\n" +
//            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMpjaWjngB4E3ATh+G1DVAmQnIp\n" +
//            "iPEFAEDqRfNGAVvvH35yDetqewKi0l7OEceTMN1C6NPym3zStvSoQayjYV+eIcZER\n" +
//            "kx31KhtFu9clZKgRTyPjdKMIth/wBtPKjL/5+PYalLdomM4ONthrPgnkN4x4R0+D4\n" +
//            "+EBpXo8gNiAFsNwIDAQAB\n" +
//            "##############end public key##############";
protected static String APIGW_PUBLIC_KEY =
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMpjaWjngB4E3ATh+G1DVAmQnIp\n" +
        "iPEFAEDqRfNGAVvvH35yDetqewKi0l7OEceTMN1C6NPym3zStvSoQayjYV+eIcZER\n" +
        "kx31KhtFu9clZKgRTyPjdKMIth/wBtPKjL/5+PYalLdomM4ONthrPgnkN4x4R0+D4\n" +
        "+EBpXo8gNiAFsNwIDAQAB\n" ;
    protected static String PRI_KEY = "7222f6f6d32195f08bf29c6fb281c88d95539426066b4136080dba0a1545ea3d";
    protected static String BASE_URL = "https://gw.open.icbc.com.cn"; // api地址
    protected static String CA_PRIVATE_STR = "7222f6f6d32195f08bf29c6fb281c88d95539426066b4136080dba0a1545ea3d";
    protected static String CA_PUBLIC_STR = "MIICVDCCAfigAwIBAgIGAJuA0pwHMAwGCCqBHM9VAYN1BQAwQTELMAkGA1UEBhMCY24xGzAZBgNV\n" +
            "BAoMEnNtMmNvci5pY2JjLmNvbS5jbjEVMBMGA1UEAwwMc20ycm9vdGNhY29yMB4XDTI1MTIzMDA2\n" +
            "NDkzMloXDTMwMTIzMDE1NTk1OVowYDELMAkGA1UEBhMCY24xGzAZBgNVBAoMEnNtMmNvci5pY2Jj\n" +
            "LmNvbS5jbjENMAsGA1UECwwEMTIwMjElMCMGA1UEAwwcMTk5MDAwMjQwNDUwMDAwMy55LjEyMDIu\n" +
            "MDIwMTBZMBMGByqGSM49AgEGCCqBHM9VAYItA0IABN9us5TohlCjbP3eVzHhslou3kefe7iob1Jg\n" +
            "GSsA4fNINZF/x7Wq4kVXg14vCiE+qcbNI5KlWw1hcUY9s+Cev3KjgbowgbcwHwYDVR0jBBgwFoAU\n" +
            "DD6RRlIIkxjZ0/0x6S8mKdW3D2YwCQYDVR0TBAIwADBdBgNVHR8EVjBUMFKgUKBOpEwwSjEQMA4G\n" +
            "A1UEAwwHY3JsNjY4NDEMMAoGA1UECwwDY3JsMRswGQYDVQQKDBJzbTJjb3IuaWNiYy5jb20uY24x\n" +
            "CzAJBgNVBAYTAmNuMAsGA1UdDwQEAwIHgDAdBgNVHQ4EFgQUI6/OOSv5WHiRx2L9Re5DpxQ1hpgw\n" +
            "DAYIKoEcz1UBg3UFAANIADBFAiEAmdxytr2dKDTsIYDVzCkShyy8Oy3ZJwQ7QQJI0ZnoMxECIB5Z\n" +
            "bo9J9IydAsI8FUnh+Ao7kfhsDhCDZ/CiFgTWkQ0u";
    protected static String CA_PASSWORD = "12345678";

    public static void main(String[] args) throws Exception {
        String icbcCa = loadCertificate(
                "/API_GATEWAY_ICBC_SM.cer"
        );
        String URI = "api/mybank/enterprise/bill/qstockbill/V1";
//        DefaultIcbcClient client = new DefaultIcbcClient(APP_ID,PRI_KEY,APIGW_PUBLIC_KEY);
//         DefaultIcbcClient client = new DefaultIcbcClient(APP_ID, CA_PRIVATE_STR, APIGW_PUBLIC_KEY, CA_PUBLIC_STR, CA_PASSWORD);
        DefaultIcbcClient client = new DefaultIcbcClient(
                APP_ID,
                "CA-SM-ICBC",
                CA_PRIVATE_STR,
                "UTF-8",
                "json",
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
            bizContent.setTranDate("20260901"); // 交易日期：ERP系统产生，格式是yyyyMMdd
            bizContent.setTranTime("103231001"); // 交易时间：ERP系统产生，格式如HHmmssSSS，精确到毫秒
            bizContent.setLanguage("zh_CN"); // 语言：zh_CN简体中文，en_US英文，默认zh_CN
            bizContent.setfSeqNo("QSTOCKBILL1022393036"); // 指令包序列号：ERP系统产生，一个集团永远不能重复
//            bizContent.setHolderAcctId("0200003309004709145"); // 持票人账号
//            bizContent.setPackAmtBgn(0L); // 票据（包）金额起（指票面金额）：单位:分
//            bizContent.setPackAmtEnd(100000000000L); // 票据（包）金额止（指票面金额）：单位:分
            bizContent.setCdTp("AC01"); // 票据种类：AC01银承,AC02商承,0为查询全部
            bizContent.setDueDateBgn("20260101"); // 票据到期日开始：格式为yyyyMMdd，到期日开始和结束之间不超过一年
            bizContent.setDueDateEnd("20260901"); // 票据到期日结束：格式为yyyyMMdd，到期日开始和结束之间不超过一年
//            bizContent.setHoldDateBgn("20220103"); // 票据持有开始日期：格式为yyyyMMdd
//            bizContent.setHoldDateEnd("20220503"); // 票据持有结束日期：格式为yyyyMMdd
//            bizContent.setIssueDateBgn("20220106"); // 出票开始日期：格式为yyyyMMdd
//            bizContent.setIssueDateEnd("20220506"); // 出票结束日期：格式为yyyyMMdd
//            bizContent.setAccptrName("中国工商银行股份有限公司杭州支行"); // 承兑人名称
//            bizContent.setDrwrName("杭州染料有限公司"); // 出票人名称
//            bizContent.setPreName("杭州生物科技有限公司"); // 前手背书人名称
//            bizContent.setNextTag("2"); // 查询下一页标识：供客户翻页时上送的字段
            List<MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestRdV1> rdList = new ArrayList<>(); // 循环区：最多支持100个
            MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestRdV1 rd = new MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestRdV1();
            rd.setPackNo("110233520800020100421000113497"); // 票据包号：每笔票据号码长度为30；
//            rdList.add(rd);
            bizContent.setRd(rdList);
            request.setServiceUrl(BASE_URL + "/" + URI);
            request.setBizContent(bizContent);
            MybankEnterpriseBillQstockbillResponseV1 response = client.execute(request);
            System.out.println("response:"+ JSON.toJSONString(response));
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
