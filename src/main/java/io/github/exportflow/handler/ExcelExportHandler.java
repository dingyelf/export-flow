package io.github.exportflow.handler;

import io.github.exportflow.common.PageResult;
import io.github.exportflow.dto.template.ExportTemplate;

import java.util.List;

public interface ExcelExportHandler<T> {

    String getSheetName();

    List<ExportTemplate> getHeaders();

    String getFileName();

    PageResult<T> queryPage(int pageNum, int pageSize);

}
