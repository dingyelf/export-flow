package io.github.exportflow.controller;

import io.github.exportflow.entity.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/export")
public class ExportController {

    @RequestMapping("/users")
    public void exportUsers(HttpServletResponse response) throws UnsupportedEncodingException {
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();

        // 创建工作表
        Sheet sheet = workbook.createSheet("用户数据");

        // 创建表头
        Row row = sheet.createRow(0);
        List<String> headers = getHeaders();
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers.get(i));
        }

        // 创建数据行
        List<User> userData = getUserData();
        for (int i = 0; i < userData.size(); i++) {
            row = sheet.createRow(i+1);

            int k = 0;
            row.createCell(k++).setCellValue(userData.get(i).getId());
            row.createCell(k++).setCellValue(userData.get(i).getUsername());
            row.createCell(k++).setCellValue(userData.get(i).getAge());
            row.createCell(k++).setCellValue(userData.get(i).getAddress());
        }

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = URLEncoder.encode("用户数据.xlsx", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        // 输出流
        try (OutputStream outputStream = response.getOutputStream()){
            workbook.write(outputStream);
            outputStream.flush();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> getHeaders() {
        return Arrays.asList("ID", "姓名", "年龄", "地址");
    }

    private List<User> getUserData() {
        return Arrays.asList(
                new User(2, "张三", 18, "北京"),
                new User(1, "李四", 19, "上海"),
                new User(3, "王五", 20, "广州"),
                new User(4, "赵六", 21, "深圳")
        );
    }

}
