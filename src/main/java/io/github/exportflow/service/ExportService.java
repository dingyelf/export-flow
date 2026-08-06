package io.github.exportflow.service;

import io.github.exportflow.common.PageResult;
import io.github.exportflow.entity.Book;
import io.github.exportflow.entity.User;
import io.github.exportflow.utils.data.BookDataUtils;
import io.github.exportflow.utils.data.UserDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class ExportService {

    @Resource
    private UserDataUtils userDataUtils;

    @Resource
    private BookDataUtils bookDataUtils;

    public PageResult<Book> queryBookByPage(Long pageNum, int pageSize) {
        log.info("queryBookByPage start with param: pageNum = {}, pageSize = {}", pageNum, pageSize);
        List<Book> bookData = bookDataUtils.getBookData();
        PageResult<Book> pageResult = PageResult.paginate(bookData, pageNum, pageSize);
        log.info("queryBookByPage end");
        return pageResult;
    }


    public PageResult<User> queryUsersByPage(Long pageNum, int pageSize) {
        log.info("queryUsersByPage start with param: pageNum = {}, pageSize = {}", pageNum, pageSize);
        List<User> userData = userDataUtils.getUserData();
        PageResult<User> paginate = PageResult.paginate(userData, pageNum, pageSize);
        log.info("queryUsersByPage end");
        return paginate;
    }

}
