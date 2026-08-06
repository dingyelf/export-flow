package io.github.exportflow.handler;

import io.github.exportflow.common.PageResult;
import io.github.exportflow.dto.template.ExportTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ExcelExportHandler<T> {

    String getSheetName();

    List<ExportTemplate> getHeaders();

    String getFileName();

    List<T> getData(PageResult<T> list);

    PageResult<T> queryPage(int pageNum, int pageSize);

}
