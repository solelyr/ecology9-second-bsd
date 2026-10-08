package com.api;

import weaver.general.GCONST;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 * @DESCRIPTION:
 * @USER: solelyr
 * @DATE: 2026/10/8 9:58
 */
@Path("/second")
public class SecondApi {
    @GET
    @Path("/getlog")
    @Produces("application/zip")
    public Response getLog() {
        try {
            final String rootPath = GCONST.getRootPath();
            String fileSeparator = File.separator;
            List<File> filesToZip = Arrays.asList(
                    new File(rootPath + "log" + fileSeparator + "solelyrSecond" + fileSeparator + "solelyrSecond.log")
            );

            // 2. 使用 StreamingOutput 实现边压边传
            StreamingOutput streamingOutput = new StreamingOutput() {
                @Override
                public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                    try (ZipOutputStream zos = new ZipOutputStream(outputStream)) {
                        byte[] buffer = new byte[8192];

                        for (File file : filesToZip) {
                            if (!file.exists() || !file.isFile()) {
                                continue; // 跳过不存在或非法的文件
                            }

                            // 为 ZIP 包内创建条目
                            ZipEntry zipEntry = new ZipEntry(file.getName());
                            zos.putNextEntry(zipEntry);

                            // 读取文件写入 ZipOutputStream
                            try (FileInputStream fis = new FileInputStream(file)) {
                                int length;
                                while ((length = fis.read(buffer)) > 0) {
                                    zos.write(buffer, 0, length);
                                }
                            }
                            zos.closeEntry();
                        }
                        zos.finish();
                    }
                }
            };

            // 3. 处理中文文件名编码，避免浏览器下载乱码
            String rawFileName = "自定义开发日志文件.zip";
            String encodedFileName = URLEncoder.encode(rawFileName, StandardCharsets.UTF_8.name())
                    .replace("+", "%20");

            // 4. 构建 Response 并添加下载头
            return Response.ok(streamingOutput)
                    .type("application/zip")
                    .header("Content-Disposition", "attachment; filename=\"" + rawFileName + "\"; filename*=UTF-8''" + encodedFileName)
                    .build();
        }catch(Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .type(MediaType.TEXT_PLAIN + "; charset=utf-8")
                    .entity("文件打包下载失败: " + e.getMessage())
                    .build();
        }
    }
}
