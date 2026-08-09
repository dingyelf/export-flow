package io.github.exportflow.controller;

import io.github.exportflow.handler.impl.BookExportHandler;
import io.github.exportflow.handler.impl.UserExportHandler;
import io.github.exportflow.service.ExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

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

    @RequestMapping("books")
    public void exportBooks(HttpServletResponse response) throws UnsupportedEncodingException {
        log.info("start export book data");
        exportService.exportData(bookExportHandler, 1, 2, response);
        log.info("end export book data");
    }

    @RequestMapping("/users")
    public void exportUsers(HttpServletResponse response) throws UnsupportedEncodingException {
        log.info("start export user data");
        exportService.exportData(userExportHandler, 1, 2, response);
        log.info("end export user data");
    }

}
