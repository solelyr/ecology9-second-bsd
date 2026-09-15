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

    protected static String APP_ID = "[需替换]工行APP编号";
    protected static String APIGW_PUBLIC_KEY = "[需替换]工行API网关公钥，请向工行联系获取";
    protected static String PRI_KEY = "[需替换]应用方私钥，由应用方生成公私钥对，并将公钥上传至工行API开放平台";
    protected static String BASE_URL = "[需替换]工行API地址"; // api地址

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
