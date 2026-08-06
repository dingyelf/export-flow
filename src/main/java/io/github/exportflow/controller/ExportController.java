package io.github.exportflow.controller;

import io.github.exportflow.common.PageResult;
import io.github.exportflow.dto.template.ExportTemplate;
import io.github.exportflow.handler.ExcelExportHandler;
import io.github.exportflow.handler.impl.BookExportHandler;
import io.github.exportflow.handler.impl.UserExportHandler;
import io.github.exportflow.service.ExportService;
import io.github.exportflow.utils.ReflectUtils;
import io.github.exportflow.utils.data.BookDataUtils;
import io.github.exportflow.utils.data.UserDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/export")
public class ExportController {

    @Resource
    private UserDataUtils userDataUtils;

    @Resource
    private BookDataUtils bookDataUtils;

    @Resource
    private ExportService exportService;

    @Resource
    private UserExportHandler userExportHandler;

    @Resource
    private BookExportHandler bookExportHandler;

    @RequestMapping("books")
    public void exportBooks(HttpServletResponse response) throws UnsupportedEncodingException {
        log.info("start export book data");
        export(bookExportHandler, 1, 2, response);
        log.info("end export book data");
    }

    @RequestMapping("/users")
    public void exportUsers(HttpServletResponse response) throws UnsupportedEncodingException {
        log.info("start export user data");
        export(userExportHandler, 1, 2, response);
        log.info("end export user data");
    }

    private <T> void export(ExcelExportHandler excelExportHandler, Integer pageNum, Integer pageSize, HttpServletResponse response) {
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();

        // 创建工作表
        Sheet sheet = workbook.createSheet(excelExportHandler.getSheetName());

        // 创建表头
        List<ExportTemplate> headers = excelExportHandler.getHeaders();
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            row.createCell(i).setCellValue(headers.get(i).getTitle());
        }

        // 创建数据行
        PageResult<T> pageResult = excelExportHandler.queryPage(pageNum, pageSize);
        int rowNum = 1;
        do {
            List<T> list = pageResult.getList();
            for (int i = 0; i < list.size(); i++) {
                row = sheet.createRow(rowNum);
                for (int j = 0; j < headers.size(); j++) {
                    String fieldValue = ReflectUtils.getFieldValue(list.get(i), headers.get(j).getColumn());
                    row.createCell(j).setCellValue(fieldValue);
                }
                rowNum++;
            }
        } while (pageResult.hasNextPage() && (pageResult = excelExportHandler.queryPage(pageResult.getCurrentPage() + 1, pageSize)) != null);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = URLEncoder.encode(excelExportHandler.getFileName(), StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        // 输出流
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            outputStream.flush();
            workbook.close();
        } catch (Exception e) {
            log.error("export err", e);
        }
    }

}
