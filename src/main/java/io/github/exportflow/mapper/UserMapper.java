package io.github.exportflow.mapper;

import io.github.exportflow.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT COUNT(*) FROM user")
    int count();

    @Select("SELECT id, username, gender, age, email, address, phone FROM user LIMIT #{offset}, #{pageSize}")
    List<User> queryUser(@Param("offset") int offset, @Param("pageSize") int pageSize);
}