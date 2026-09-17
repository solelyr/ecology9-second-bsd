package com.engine.icbc;

import com.icbc.api.DefaultIcbcClient;
import com.icbc.api.request.MybankEnterpriseBillQstockbillRequestV1;
import com.icbc.api.response.MybankEnterpriseBillQstockbillResponseV1;

import java.util.ArrayList;
import java.util.List;

/**
 * @DESCRIPTION:
 * @USER: solelyr
 * @DATE: 2026/9/15 21:23
 */
public class MybankEnterpriseBillQstockbillTest {

    protected static String APP_ID = "11000000000000073859";
    protected static String APIGW_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMpjaWjngB4E3ATh+G1DVAmQnIp\n" +
            "iPEFAEDqRfNGAVvvH35yDetqewKi0l7OEceTMN1C6NPym3zStvSoQayjYV+eIcZER\n" +
            "kx31KhtFu9clZKgRTyPjdKMIth/wBtPKjL/5+PYalLdomM4ONthrPgnkN4x4R0+D4\n" +
            "+EBpXo8gNiAFsNwIDAQAB";
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
        String URI = "mybank/enterprise/bill/qstockbill/V1";
         DefaultIcbcClient client = new DefaultIcbcClient(APP_ID, CA_PRIVATE_STR, APIGW_PUBLIC_KEY, CA_PUBLIC_STR, CA_PASSWORD);
        try {
            MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestBizV1 bizContent =
                    new MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestBizV1();
            MybankEnterpriseBillQstockbillRequestV1 request = new MybankEnterpriseBillQstockbillRequestV1();
            // 请对照接口文档用bizContent.setxxx()方法对业务上送数据进行赋值
            bizContent.setTransCode("QSTOCKBILL");
            bizContent.setTranDate("20190101");
            bizContent.setTranTime("103231001");
            bizContent.setLanguage("zh_CN");
            bizContent.setfSeqNo("QSTOCKBILL1022393036");
            bizContent.setHolderAcctId("0200003309004709145");
            bizContent.setPackAmtBgn(0L);
            bizContent.setPackAmtEnd(100000000000L);
            bizContent.setCdTp("AC01");
            bizContent.setDueDateBgn("20220101");
            bizContent.setDueDateEnd("20220501");
            bizContent.setHoldDateBgn("20220103");
            bizContent.setHoldDateEnd("20220503");
            bizContent.setIssueDateBgn("20220106");
            bizContent.setIssueDateEnd("20220506");
            bizContent.setAccptrName("中国工商银行股份有限公司杭州支行");
            bizContent.setDrwrName("杭州染料有限公司");
            bizContent.setPreName("杭州生物科技有限公司");
            bizContent.setNextTag("2");
            List<MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestRdV1> rdList = new ArrayList<>();
            MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestRdV1 rd = new MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestRdV1();
            rd.setPackNo("110233520800020100421000113497");
            rdList.add(rd);
            bizContent.setRd(rdList);
            request.setServiceUrl(BASE_URL + "/" + URI);
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
}
