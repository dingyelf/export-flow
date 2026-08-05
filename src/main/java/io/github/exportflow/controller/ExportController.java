package io.github.exportflow.controller;

import io.github.exportflow.dto.ExportUserTemplate;
import io.github.exportflow.entity.User;
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
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/export")
public class ExportController {

    @Resource
    private UserDataUtils userDataUtils;

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

        // 创建数据行
        List<User> userData = userDataUtils.getUserData();
        for (int i = 0; i < userData.size(); i++) {
            row = sheet.createRow(i + 1);

            int k = 0;
            while (k < exportUsersTemplate.size()) {
                String value = ReflectUtils.getFieldValue(userData.get(i), exportUsersTemplate.get(k).getColumn());
                row.createCell(k).setCellValue(value);
                k++;
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

    private List<String> getHeaders() {
        return Arrays.asList("ID", "姓名", "年龄", "地址");
    }

}
