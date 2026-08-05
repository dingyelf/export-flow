package io.github.exportflow.utils;

import io.github.exportflow.dto.ExportUserTemplate;
import io.github.exportflow.entity.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserDataUtils {

    public List<ExportUserTemplate> getExportUsersTemplate() {
        return Arrays.asList(
                new ExportUserTemplate("姓名", "username"),
                new ExportUserTemplate("性别", "sex"),
                new ExportUserTemplate("年龄", "age"),
                new ExportUserTemplate("邮箱", "email"),
                new ExportUserTemplate("地址", "address")
        );
    }

    public List<User> getUserData() {
        return Arrays.asList(
                new User(1, "张三", "man", 18, "zhangsan@email.com", "北京", "18212340001"),
                new User(2, "李四", "man", 19, "lisi@email.com", "上海", "18212340002"),
                new User(3, "王五", "man", 20, "wangwu@email.com", "深圳", "18212340003"),
                new User(3, "赵六", "man", 21, "zhaoliu@email.com", "杭州", "18212340004")
        );
    }
}
