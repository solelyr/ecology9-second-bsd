package icbc;

import base.BaseTest;
import com.alibaba.fastjson.JSONObject;
import com.icbc.api.DefaultIcbcClient;
import com.icbc.api.request.MybankEnterpriseBillQstockbillRequestV1;
import com.icbc.api.response.MybankEnterpriseBillQstockbillResponseV1;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import weaver.file.Prop;
import weaver.general.Util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @DESCRIPTION:
 * @USER: solelyr
 * @DATE: 2026/9/18 21:14
 */
public class MybankEnterpriseBillQstockbillTest extends BaseTest {
    private static final String EXPORT_FILE_NAME = "mybank-enterprise-bill-qstockbill.xlsx";

    @Test
    public void test() {
        final String CONFIG_RESOURCE = "solelyr_bsd_icbc_bill";
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
            List<MybankEnterpriseBillQstockbillResponseV1.MybankEnterpriseBillQstockbillResponseRdV1> list = new ArrayList<>();
            String nextTag = "-1";
            while (!"".equals(nextTag)){
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
                bizContent.setNextTag("-1".equals(nextTag) ? "" : nextTag); // 查询下一页标识：供客户翻页时上送的字段
                List<MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestRdV1> rdList = new ArrayList<>(); // 循环区：最多支持100个
                MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestRdV1 rd = new MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestRdV1();
//                rd.setPackNo("110233520800020100421000113497"); // 票据包号：每笔票据号码长度为30；
//            rdList.add(rd);
                bizContent.setRd(rdList);
                request.setServiceUrl(BASE_URL + "/" + SERVICE_PATH);
                request.setBizContent(bizContent);
                MybankEnterpriseBillQstockbillResponseV1 response = client.execute(request);
                System.out.println("response:"+ JSONObject.toJSONString(response));

                if (response.isSuccess()) {
                    // 业务成功处理
                    list.addAll(response.getRd());
                    nextTag = response.getNextTag();
                    System.out.println("success");
                } else {
                    // 失败
                    nextTag = "";
                    System.out.println("error: " + response.getReturnMsg());
                }
            }
            Path outputPath = exportToExcel(list);
            System.out.println("Excel文件已生成：" + outputPath.toAbsolutePath());

        } catch (Exception e) {
            throw new RuntimeException("查询或导出票据数据失败", e);
        }
    }

    private static Path exportToExcel(
            List<MybankEnterpriseBillQstockbillResponseV1.MybankEnterpriseBillQstockbillResponseRdV1> list)
            throws Exception {
        List<PropertyDescriptor> properties = Arrays.stream(
                        Introspector.getBeanInfo(
                                        MybankEnterpriseBillQstockbillResponseV1
                                                .MybankEnterpriseBillQstockbillResponseRdV1.class,
                                        Object.class)
                                .getPropertyDescriptors())
                .filter(property -> property.getReadMethod() != null)
                .sorted((left, right) -> left.getName().compareTo(right.getName()))
                .collect(Collectors.toList());

        Path outputDirectory = Paths.get(System.getProperty("user.dir"), "target", "test-output");
        Files.createDirectories(outputDirectory);
        Path outputPath = outputDirectory.resolve(EXPORT_FILE_NAME);

        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream outputStream = new FileOutputStream(outputPath.toFile())) {
            Sheet sheet = workbook.createSheet("票据查询结果");
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(workbook.getCreationHelper()
                    .createDataFormat().getFormat("yyyy-mm-dd"));

            Row headerRow = sheet.createRow(0);
            for (int columnIndex = 0; columnIndex < properties.size(); columnIndex++) {
                Cell cell = headerRow.createCell(columnIndex);
                cell.setCellValue(properties.get(columnIndex).getName());
                cell.setCellStyle(headerStyle);
            }

            for (int rowIndex = 0; rowIndex < list.size(); rowIndex++) {
                Row row = sheet.createRow(rowIndex + 1);
                Object item = list.get(rowIndex);
                for (int columnIndex = 0; columnIndex < properties.size(); columnIndex++) {
                    PropertyDescriptor property = properties.get(columnIndex);
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(item);
                    writeCell(row.createCell(columnIndex), property.getName(), value, dateStyle);
                }
            }

            sheet.createFreezePane(0, 1);
            if (!properties.isEmpty()) {
                sheet.setAutoFilter(new CellRangeAddress(0, list.size(), 0, properties.size() - 1));
            }
            for (int columnIndex = 0; columnIndex < properties.size(); columnIndex++) {
                sheet.autoSizeColumn(columnIndex);
                if (sheet.getColumnWidth(columnIndex) > 40 * 256) {
                    sheet.setColumnWidth(columnIndex, 40 * 256);
                }
            }

            workbook.write(outputStream);
        }
        return outputPath;
    }

    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        return style;
    }

    private static void writeCell(Cell cell, String propertyName, Object value, CellStyle dateStyle) {
        if (value == null) {
            cell.setCellType(CellType.BLANK);
            return;
        }
        if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
            return;
        }
        if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
            return;
        }
        String text = String.valueOf(value);
        if (propertyName.endsWith("Date")) {
            try {
                LocalDate localDate = LocalDate.parse(text, DateTimeFormatter.BASIC_ISO_DATE);
                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                cell.setCellValue(date);
                cell.setCellStyle(dateStyle);
                return;
            } catch (DateTimeParseException ignored) {
                // 非 yyyyMMdd 日期保留接口原值。
            }
        }
        cell.setCellValue(text);
    }
}
