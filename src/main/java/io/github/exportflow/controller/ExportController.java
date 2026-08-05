package io.github.exportflow.controller;

import io.github.exportflow.dto.template.ExportUserTemplate;
import io.github.exportflow.entity.User;
import io.github.exportflow.service.ExportService;
import io.github.exportflow.common.PageResult;
import io.github.exportflow.utils.ReflectUtils;
import io.github.exportflow.utils.UserDataUtils;
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
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/export")
public class ExportController {

    @Resource
    private UserDataUtils userDataUtils;

    @Resource
    private ExportService exportService;

    @RequestMapping("/users")
    public void exportUsers(HttpServletResponse response) throws UnsupportedEncodingException {
        log.info("start export user data");
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();

        // 创建工作表
        Sheet sheet = workbook.createSheet("用户数据");

        // 创建表头
        List<ExportUserTemplate> exportUsersTemplate = userDataUtils.getExportUsersTemplate();

        Row row = sheet.createRow(0);
        for (int i = 0; i < exportUsersTemplate.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(exportUsersTemplate.get(i).getTitle());
        }

        long pageNum = 1;
        int pageSize = 2;

        // 创建数据行
        PageResult<User> userPageResult = exportService.queryUsersByPage(pageNum, pageSize);

        int rowNum = 1;

        List<User> userData = userPageResult.getList();
        for (int i = 0; i < userData.size(); i++) {
            row = sheet.createRow(rowNum);
            int k = 0;
            while (k < exportUsersTemplate.size()) {
                String value = ReflectUtils.getFieldValue(userData.get(i), exportUsersTemplate.get(k).getColumn());
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
                while (k < exportUsersTemplate.size()) {
                    String value = ReflectUtils.getFieldValue(userData.get(i), exportUsersTemplate.get(k).getColumn());
                    row.createCell(k).setCellValue(value);
                    k++;
                }
                rowNum++;
            }
        }

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = URLEncoder.encode("用户数据.xlsx", "UTF-8");
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

}
