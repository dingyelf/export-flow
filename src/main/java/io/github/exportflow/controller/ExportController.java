package io.github.exportflow.controller;

import io.github.exportflow.common.PageResult;
import io.github.exportflow.dto.template.ExportTemplate;
import io.github.exportflow.entity.Book;
import io.github.exportflow.entity.User;
import io.github.exportflow.handler.ExcelExportHandler;
import io.github.exportflow.service.ExportService;
import io.github.exportflow.utils.ReflectUtils;
import io.github.exportflow.utils.data.BookDataUtils;
import io.github.exportflow.utils.data.UserDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
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

    @RequestMapping("books")
    public void exportBooks(HttpServletResponse response) throws UnsupportedEncodingException {
        log.info("start export book data");

        // 获取表头
        List<ExportTemplate> headers = bookDataUtils.getHeaders();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("图书数据");
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers.get(i).getTitle());
        }

        int pageSize = 2;
        PageResult<Book> bookPageResult = exportService.queryBookByPage(1, pageSize);
        int rowNum = 1;
        List<Book> bookData = bookPageResult.getList();
        for (int i = 0; i < bookData.size(); i++) {
            row = sheet.createRow(rowNum);
            for (int j = 0; j < headers.size(); j++) {
                String fieldValue = ReflectUtils.getFieldValue(bookData.get(i), headers.get(j).getColumn());
                row.createCell(j).setCellValue(fieldValue);
            }
            rowNum++;
        }

        while (bookPageResult.hasNextPage()) {
            bookPageResult = exportService.queryBookByPage(bookPageResult.getCurrentPage() + 1, pageSize);
            bookData = bookPageResult.getList();
            for (int i = 0; i < bookData.size(); i++) {
                row = sheet.createRow(rowNum);
                for (int j = 0; j < headers.size(); j++) {
                    String fieldValue = ReflectUtils.getFieldValue(bookData.get(i), headers.get(j).getColumn());
                    row.createCell(j).setCellValue(fieldValue);
                }
                rowNum++;
            }
        }

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = URLEncoder.encode("图书数据.xlsx", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        // 输出流
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            outputStream.flush();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("end export book data");
    }

    @RequestMapping("/users")
    public void exportUsers(HttpServletResponse response) throws UnsupportedEncodingException {
        log.info("start export user data");
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();

        // 创建工作表
        Sheet sheet = workbook.createSheet("用户数据");

        // 创建表头
        List<ExportTemplate> headers = userDataUtils.getHeaders();

        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers.get(i).getTitle());
        }

        Integer pageNum = 1;
        int pageSize = 2;

        // 创建数据行
        PageResult<User> userPageResult = exportService.queryUsersByPage(pageNum, pageSize);

        int rowNum = 1;

        List<User> userData = userPageResult.getList();
        for (int i = 0; i < userData.size(); i++) {
            row = sheet.createRow(rowNum);
            int k = 0;
            while (k < headers.size()) {
                String value = ReflectUtils.getFieldValue(userData.get(i), headers.get(k).getColumn());
                row.createCell(k).setCellValue(value);
                k++;
            }
            rowNum++;
        }

        while (userPageResult.hasNextPage()) {
            userPageResult = exportService.queryUsersByPage(userPageResult.getCurrentPage() + 1, pageSize);
            userData = userPageResult.getList();
            for (int i = 0; i < userData.size(); i++) {
                row = sheet.createRow(rowNum);
                int k = 0;
                while (k < headers.size()) {
                    String value = ReflectUtils.getFieldValue(userData.get(i), headers.get(k).getColumn());
                    row.createCell(k).setCellValue(value);
                    k++;
                }
                rowNum++;
            }
        }

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = URLEncoder.encode("用户数据.xlsx", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        // 输出流
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            outputStream.flush();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                sheet.createRow(rowNum);
                for (int j = 0; j < headers.size(); j++) {
                    String fieldValue = ReflectUtils.getFieldValue(list.get(i), headers.get(j).getColumn());
                    row.createCell(j).setCellValue(fieldValue);
                }
            }
            rowNum++;
        } while (pageResult != null && (pageResult = excelExportHandler.queryPage(pageResult.getCurrentPage() + 1, pageSize)).hasNextPage());

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = URLEncoder.encode("用户数据.xlsx", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        // 输出流
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            outputStream.flush();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
