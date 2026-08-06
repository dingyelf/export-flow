package io.github.exportflow.utils.data;

import io.github.exportflow.dto.template.ExportTemplate;
import io.github.exportflow.entity.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserDataUtils {

    public List<ExportTemplate> getExportUsersTemplate() {
        return Arrays.asList(
                new ExportTemplate("姓名", "username"),
                new ExportTemplate("性别", "gender"),
                new ExportTemplate("年龄", "age"),
                new ExportTemplate("邮箱", "email"),
                new ExportTemplate("地址", "address")
        );
    }

    public List<User> getUserData() {
        return Arrays.asList(
                new User(1, "张三", "man", 18, "zhangsan@email.com", "北京", "18212340001"),
                new User(2, "李四", "man", 19, "lisi@email.com", "上海", "18212340002"),
                new User(3, "王五", "man", 20, "wangwu@email.com", "深圳", "18212340003"),
                new User(3, "赵六", "man", 21, "zhaoliu@email.com", "杭州", "18212340004"),
                new User(3, "孙七", "man", 22, "sunqi@email.com", "广东", "18212340005")
        );
    }
}
