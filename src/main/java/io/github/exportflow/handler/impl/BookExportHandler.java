package io.github.exportflow.handler.impl;

import io.github.exportflow.common.PageResult;
import io.github.exportflow.dto.template.ExportTemplate;
import io.github.exportflow.entity.Book;
import io.github.exportflow.handler.ExcelExportHandler;
import io.github.exportflow.mapper.BookMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component
public class BookExportHandler implements ExcelExportHandler<Book> {

    @Resource
    private BookMapper bookMapper;

    @Override
    public String getSheetName() {
        return "图书数据";
    }

    @Override
    public List<ExportTemplate> getHeaders() {
        return Arrays.asList(
                new ExportTemplate("编号", "id"),
                new ExportTemplate("书名", "bookName"),
                new ExportTemplate("价格", "price"),
                new ExportTemplate("作者", "author"),
                new ExportTemplate("出版时间", "publishTime")
        );
    }

    @Override
    public String getFileName() {
        return "图书数据.xlsx";
    }

    @Override
    public PageResult<Book> queryPage(int pageNum, int pageSize) {
        int count = bookMapper.count();
        int offset = (pageNum - 1) * pageSize;
        List<Book> list = bookMapper.queryBook(offset, pageSize);

        PageResult<Book> result = new PageResult<>();
        result.setCount(count);
        result.setPageSize(pageSize);
        result.setCurrentPage(pageNum);
        result.setList(list);
        return result;
    }
}
