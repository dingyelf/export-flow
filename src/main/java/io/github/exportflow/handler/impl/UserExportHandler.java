package io.github.exportflow.handler.impl;

import io.github.exportflow.common.PageResult;
import io.github.exportflow.dto.template.ExportTemplate;
import io.github.exportflow.entity.User;
import io.github.exportflow.handler.ExcelExportHandler;
import io.github.exportflow.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserExportHandler implements ExcelExportHandler<User> {

    @Resource
    private UserMapper userMapper;

    @Override
    public String getSheetName() {
        return "用户数据";
    }

    @Override
    public List<ExportTemplate> getHeaders() {
        return Arrays.asList(
                new ExportTemplate("Id", "id"),
                new ExportTemplate("姓名", "username"),
                new ExportTemplate("性别", "gender"),
                new ExportTemplate("年龄", "age"),
                new ExportTemplate("邮箱", "email"),
                new ExportTemplate("地址", "address")
        );
    }

    @Override
    public String getFileName() {
        return "用户数据.xlsx";
    }

    @Override
    public PageResult<User> queryPage(int pageNum, int pageSize) {
        int count = userMapper.count();
        int offset = (pageNum - 1) * pageSize;
        List<User> list = userMapper.queryUser(offset, pageSize);

        PageResult<User> result = new PageResult<>();
        result.setCount(count);
        result.setPageSize(pageSize);
        result.setCurrentPage(pageNum);
        result.setList(list);
        return result;
    }
}
