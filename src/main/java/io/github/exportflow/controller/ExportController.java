package io.github.exportflow.controller;

import io.github.exportflow.handler.impl.BookExportHandler;
import io.github.exportflow.handler.impl.UserExportHandler;
import io.github.exportflow.service.ExportService;
import io.github.exportflow.utils.DateUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/export")
public class ExportController {

    @Resource
    private UserExportHandler userExportHandler;

    @Resource
    private BookExportHandler bookExportHandler;

    @Resource
    private ExportService exportService;

    private static final int EXPORT_PAGE_SIZE = 2000;

    @GetMapping("/books")
    public void exportBooks(HttpServletResponse response) throws IOException {
        log.info("start export book data");

        Workbook workbook = exportService.exportData(bookExportHandler, 1, EXPORT_PAGE_SIZE);

        // 设置响应头
        writeResponse(response, workbook, bookExportHandler.getFileName());

        log.info("end export book data");
    }

    @GetMapping("/users")
    public void exportUsers(HttpServletResponse response) throws IOException {
        log.info("start export user data");

        Workbook workbook = exportService.exportData(userExportHandler, 1, EXPORT_PAGE_SIZE);

        // 设置响应头
        writeResponse(response, workbook, userExportHandler.getFileName());

        log.info("end export user data");
    }

    private void writeResponse(HttpServletResponse response, Workbook workbook, String fileName) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String encodeFileName = null;
        try {
            encodeFileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (Exception e) {
            encodeFileName = DateUtils.formatDateTime(new Date());
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + encodeFileName);
        try (ServletOutputStream outputStream = response.getOutputStream()){
            workbook.write(outputStream);
            response.flushBuffer();
            workbook.close();
        } catch (IOException e) {
            log.error("write excel error", e);
        }

    }


}
