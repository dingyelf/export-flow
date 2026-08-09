package io.github.exportflow.utils;

import io.github.exportflow.dto.template.ExportTemplate;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExcelWriter {

    private static final int STREAM_WINDOW_SIZE = 100;

    public Workbook createWorkbook(String sheetName) {
        Workbook workbook = new SXSSFWorkbook(STREAM_WINDOW_SIZE);
        workbook.createSheet(sheetName);
        return workbook;
    }

    public void writeHeader(Workbook workbook, List<ExportTemplate> headers) {
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            row.createCell(i).setCellValue(headers.get(i).getTitle());
        }
    }

    public <T> void writeRows(Workbook workbook, List<T> data, List<ExportTemplate> headers) {
        Sheet sheet = workbook.getSheetAt(0);
        int startRow = sheet.getLastRowNum() + 1;
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(startRow + i);
            for (int j = 0; j < headers.size(); j++) {
                String value = ReflectUtils.getFieldValue(data.get(i), headers.get(j).getColumn());
                row.createCell(j).setCellValue(value == null ? "" : value);
            }
        }
    }

}
