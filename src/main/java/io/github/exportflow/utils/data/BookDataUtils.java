package io.github.exportflow.utils.data;

import io.github.exportflow.dto.template.ExportTemplate;
import io.github.exportflow.entity.Book;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class BookDataUtils {

    public List<ExportTemplate> getHeaders() {
        return Arrays.asList(
                new ExportTemplate("编号", "id"),
                new ExportTemplate("书名", "bookName"),
                new ExportTemplate("价格", "price"),
                new ExportTemplate("作者", "author"),
                new ExportTemplate("出版时间", "publishTime")
        );
    }

    public List<Book> getBookData() {
        return Arrays.asList(
                new Book("1", "《SpringBoot实战》", "50", "张三", new java.util.Date(), 1),
                new Book("2", "《Spring实战》", "50", "张小明", new java.util.Date(), 1),
                new Book("3", "《JavaWeb实战》", "50", "王小东", new java.util.Date(), 1),
                new Book("4", "《MyBatis实战》", "50", "李大嘴", new java.util.Date(), 1),
                new Book("5", "《MySQL实战》", "50", "赵公", new java.util.Date(), 1)
        );
    }

}
