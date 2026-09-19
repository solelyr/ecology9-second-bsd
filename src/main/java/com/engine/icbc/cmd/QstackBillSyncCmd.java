package com.engine.icbc.cmd;

import com.engine.core.exception.ECException;
import com.engine.core.interceptor.AbstractCommand;
import com.engine.core.interceptor.CommandContext;
import com.engine.icbc.entity.QstockbillResponseRdV1sup;
import com.engine.icbc.enums.IcbcPk;
import com.engine.util.EcologyRestUtilImpl;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import weaver.file.Prop;
import com.icbc.api.DefaultIcbcClient;
import com.icbc.api.request.MybankEnterpriseBillQstockbillRequestV1;
import com.icbc.api.response.MybankEnterpriseBillQstockbillResponseV1;
import com.solelyr.common.entity.EcologyRestEntity;
import com.solelyr.common.utils.EcologyRestUtil;
import com.solelyr.common.utils.LoggerUtil;
import weaver.general.Util;
import weaver.integration.logging.Logger;

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

    private final String CONFIG_RESOURCE = "SOLELYR_QSTOCK_BILL";
    private final Logger log = LoggerUtil.getLogger(QstackBillSyncCmd.class);
    private static final String EXPORT_FILE_NAME = "mybank-enterprise-bill-qstockbill.xlsx";


    public QstackBillSyncCmd(String dueDateBgn, String dueDateEnd) {
        this.dueDateBgn = dueDateBgn;
        this.dueDateEnd = dueDateEnd;
    }

    @Override
    public Boolean execute(CommandContext commandContext) {
        log.info("####调用工行票据查询接口开始！####"+dueDateBgn+"---"+dueDateEnd);
        String nextTag = "-1"; // 初始页
        List<MybankEnterpriseBillQstockbillResponseV1.MybankEnterpriseBillQstockbillResponseRdV1> list = new ArrayList<>();
        while (!"".equals(nextTag)){
            MybankEnterpriseBillQstockbillResponseV1 response = query(nextTag);
            log.info("####调用工行票据查询接口查询结果！####输入nextTag："+nextTag+"，接口返回结果："+response.isSuccess()+"，接口返回行数："+response.getTotalNum()+"，接口返回nextTag："+response.getNextTag());
            if (response.isSuccess()) {
                // 业务成功处理
                nextTag = response.getNextTag(); // 优先设置循环标示，避免中间异常
                List<MybankEnterpriseBillQstockbillResponseV1.MybankEnterpriseBillQstockbillResponseRdV1> rdV1List = response.getRd();
                list.addAll(rdV1List);
                List<QstockbillResponseRdV1sup> extendedList =
                        rdV1List.stream()
                                .map(QstockbillResponseRdV1sup::from)
                                .collect(Collectors.toList());

                List<EcologyRestEntity> ecologyRestList =
                        EcologyRestEntity.list2EcRestData(extendedList);

//                EcologyRestUtil restUtil = new EcologyRestUtilImpl();
//                Map<String,Object> resultMap = restUtil.saveOrUpdate(IcbcPk.QSTACKBILL, ecologyRestList);
//
//                log.info("####调用工行票据查询接口写入建模数据结果！本次写数量："+ecologyRestList.size()+"，建模接口返回结果：" + ("1".equals(Util.null2String(resultMap.get("status"))) ? "成功！" : "失败！"));
            } else {
                // 失败
                log.info("####调用工行票据查询接口工行返回失败！####" + response.getReturnMsg());
                throw new ECException("####调用工行票据查询接口工行返回失败！####" + response.getReturnMsg());
            }
        }
        log.info("测试输出最终的行数:"+list.size());
        try {
            Path outputPath = exportToExcel(list);
            log.info("Excel文件已生成：" + outputPath.toAbsolutePath());
        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    private MybankEnterpriseBillQstockbillResponseV1 query(String nextTag){

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

            MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestBizV1 bizContent =
                    new MybankEnterpriseBillQstockbillRequestV1.MybankEnterpriseBillQstockbillRequestBizV1();
            MybankEnterpriseBillQstockbillRequestV1 request = new MybankEnterpriseBillQstockbillRequestV1();
            // 请对照接口文档用bizContent.setxxx()方法对业务上送数据进行赋值
            bizContent.setTransCode("QSTOCKBILL"); // 交易代码
            bizContent.setTranDate("20260918"); // 交易日期：ERP系统产生，格式是yyyyMMdd
            bizContent.setTranTime("09430000"); // 交易时间：ERP系统产生，格式如HHmmssSSS，精确到毫秒
            bizContent.setLanguage("zh_CN"); // 语言：zh_CN简体中文，en_US英文，默认zh_CN
            bizContent.setfSeqNo("QSTOCKBILL1022393036"); // 指令包序列号：ERP系统产生，一个集团永远不能重复
            bizContent.setCdTp("0"); // 票据种类：AC01银承,AC02商承,0为查询全部
            bizContent.setDueDateBgn(dueDateBgn.replaceAll("-","")); // 票据到期日开始：格式为yyyyMMdd，到期日开始和结束之间不超过一年
            bizContent.setDueDateEnd(dueDateEnd.replaceAll("-","")); // 票据到期日结束：格式为yyyyMMdd，到期日开始和结束之间不超过一年
            bizContent.setNextTag("-1".equals(nextTag) ? "" : Util.null2String(nextTag)); // 查询下一页标识：供客户翻页时上送的字段。默认为空 表示第一页，后续以接口返回的为准

            bizContent.setRd(new ArrayList<>());// 循环区：最多支持100个
            request.setServiceUrl(BASE_URL + "/" + SERVICE_PATH);
            request.setBizContent(bizContent);
            return client.execute(request);
        }catch (Exception e){
            e.printStackTrace();
            log.info("####调用工行票据查询接口发生异常！####" + e.getMessage());
            throw new ECException("####调用工行票据查询接口发生异常！####" + e.getMessage() );
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
