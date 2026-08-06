package io.github.exportflow.handler.impl;

import io.github.exportflow.common.PageResult;
import io.github.exportflow.dto.template.ExportTemplate;
import io.github.exportflow.handler.ExcelExportHandler;
import io.github.exportflow.utils.data.UserDataUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class UserExportHandler implements ExcelExportHandler {

    @Resource
    private UserDataUtils userDataUtils;

    @Override
    public String getSheetName() {
        return "用户数据";
    }

    @Override
    public List<ExportTemplate> getHeaders() {
        return userDataUtils.getHeaders();
    }

    @Override
    public String getFileName() {
        return "用户数据.xlsx";
    }

    @Override
    public PageResult queryPage(int pageNum, int pageSize) {
        return PageResult.paginate(userDataUtils.getUserData(), pageNum, pageSize);
    }
}
