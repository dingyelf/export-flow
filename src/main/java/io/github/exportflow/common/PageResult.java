package io.github.exportflow.common;

import lombok.*;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageResult<T> {

    // 当前页内容
    public List<T> list;

    // 总数
    public Integer count;

    // 每页数量
    public Integer pageSize;

    // 当前页数
    private Integer currentPage;

    // 总页数
    public Integer totalPage;

    public Integer getTotalPage() {
        if (pageSize == null || pageSize <= 0) {
            return 0;
        }
        return count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
    }

    public boolean hasNextPage() {
        return currentPage < getTotalPage();
    }

    public static <T> PageResult<T> paginate(List<T> allData, Integer pageNum, Integer pageSize) {
        if (allData == null || allData.isEmpty()) {
            PageResult<T> r = new PageResult<>();
            r.setList(Collections.EMPTY_LIST);
            r.setCount(0);
            r.setPageSize(pageSize);
            r.setCurrentPage(pageNum);
            return r;
        }
        Integer count = allData.size();
        Integer fromIndex = (pageNum - 1) * pageSize;
        if (fromIndex >= count) {
            PageResult<T> r = new PageResult<>();
            r.setList(Collections.EMPTY_LIST);
            r.setCount(0);
            r.setPageSize(pageSize);
            r.setCurrentPage(pageNum);
            return r;
        }
        Integer toIndex = Math.min(fromIndex + pageSize, count);
        List<T> page = allData.subList(fromIndex, toIndex);
        PageResult<T> r = new PageResult<>();
        r.setList(page);
        r.setCount(count);
        r.setPageSize(pageSize);
        r.setCurrentPage(pageNum);
        return r;
    }

}
