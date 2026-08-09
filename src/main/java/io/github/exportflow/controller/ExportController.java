package io.github.exportflow.controller;

import io.github.exportflow.handler.impl.BookExportHandler;
import io.github.exportflow.handler.impl.UserExportHandler;
import io.github.exportflow.service.ExportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    private static final int EXPORT_PAGE_SIZE = 2;

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
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        try (ServletOutputStream outputStream = response.getOutputStream()){
            workbook.write(outputStream);
            response.flushBuffer();
            workbook.close();
        } catch (IOException e) {
            log.error("write excel error", e);
        }

    }


}
