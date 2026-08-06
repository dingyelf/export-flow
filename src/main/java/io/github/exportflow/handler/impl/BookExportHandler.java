package io.github.exportflow.handler.impl;

import io.github.exportflow.common.PageResult;
import io.github.exportflow.dto.template.ExportTemplate;
import io.github.exportflow.entity.Book;
import io.github.exportflow.handler.ExcelExportHandler;
import io.github.exportflow.utils.data.BookDataUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class BookExportHandler implements ExcelExportHandler {

    @Resource
    private BookDataUtils bookDataUtils;

    @Override
    public String getSheetName() {
        return "图书数据";
    }

    @Override
    public List<ExportTemplate> getHeaders() {
        return bookDataUtils.getHeaders();
    }

    @Override
    public String getFileName() {
        return "图书数据.xlsx";
    }

    @Override
    public List<Book> getData(PageResult data) {
        return data.getList();
    }

    @Override
    public PageResult queryPage(int pageNum, int pageSize) {
        return PageResult.paginate(bookDataUtils.getBookData(), pageNum, pageSize);
    }
}
