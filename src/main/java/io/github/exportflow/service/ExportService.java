package io.github.exportflow.service;

import io.github.exportflow.common.PageResult;
import io.github.exportflow.handler.ExcelExportHandler;
import io.github.exportflow.utils.ExcelWriter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExportService {

    @Resource
    private ExcelWriter excelWriter;

    public <T> Workbook exportData(ExcelExportHandler handler, Integer pageNum, Integer pageSize) {
        // 创建工作簿
        Workbook workbook = excelWriter.createWorkbook(handler.getSheetName());

        // 创建表头
        excelWriter.writeHeader(workbook, handler.getHeaders());

        // 创建数据行
        PageResult<T> pageResult = handler.queryPage(pageNum, pageSize);
        do {
            excelWriter.writeRows(workbook, pageResult.getList(), handler.getHeaders());
        } while (pageResult.hasNextPage() &&
                (pageResult = handler.queryPage(pageResult.getCurrentPage() + 1, pageSize)) != null);

        return workbook;
    }

}
