package io.github.exportflow.mapper;

import io.github.exportflow.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select("SELECT COUNT(*) FROM book")
    int count();

    @Select("SELECT id, book_name, price, author, publish_time, status FROM book LIMIT #{offset}, #{pageSize}")
    List<Book> queryBook(@Param("offset") int offset, @Param("pageSize") int pageSize);
}
