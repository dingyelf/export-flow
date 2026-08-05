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
        List<Book> bookData = bookDataUtils.getBookData();
        Long count = (long) bookData.size();
        PageResult<Book> pageResult = new PageResult<>();
        pageResult.setCurrentPage(pageNum == 0 ? 1 : pageNum);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalPage((long) (count / pageSize + 1));
        pageResult.setCount(count);
        pageResult.setList(bookData.subList((int) ((pageNum - 1) * pageSize), (int) ((pageNum * (long) pageSize) > count ? count : (pageNum * pageSize))));
        return pageResult;
    }


    public PageResult<User> queryUsersByPage(Long pageNum, int pageSize) {
        log.info("queryUsersByPage start with param: pageNum = {}, pageSize = {}", pageNum, pageSize);
        List<User> userData = userDataUtils.getUserData();
        Long count = (long) userData.size();
        PageResult<User> pageResult = new PageResult<>();
        pageResult.setCurrentPage(pageNum == 0 ? 1 : pageNum);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalPage((long) (count / pageSize + 1));
        pageResult.setCount(count);
        pageResult.setList(userData.subList((int) ((pageNum - 1) * pageSize), (int) ((pageNum * (long) pageSize) > count ? count : (pageNum * pageSize))));
        log.info("queryUsersByPage end");
        return pageResult;
    }

}
