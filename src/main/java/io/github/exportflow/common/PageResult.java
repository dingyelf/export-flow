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
    public Long count;

    // 每页数量
    public Integer pageSize;

    // 当前页数
    private Long currentPage;

    // 总页数
    public Long totalPage;

    public Long getTotalPage() {
        if (pageSize == null || pageSize <= 0) {
            return 0L;
        }
        return count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
    }

    public boolean hasNextPage() {
        return currentPage < getTotalPage();
    }

    public static <T> PageResult<T> paginate(List<T> allData, Long pageNum, Integer pageSize) {
        if (allData == null || allData.isEmpty()) {
            PageResult<T> r = new PageResult<>();
            r.setList(Collections.EMPTY_LIST);
            r.setCount(0L);
            r.setPageSize(pageSize);
            r.setCurrentPage(pageNum);
            return r;
        }
        long count = allData.size();
        long fromIndex = (pageNum - 1) * pageSize;
        if (fromIndex >= count) {
            PageResult<T> r = new PageResult<>();
            r.setList(Collections.EMPTY_LIST);
            r.setCount(0L);
            r.setPageSize(pageSize);
            r.setCurrentPage(pageNum);
            return r;
        }
        long toIndex = Math.min(fromIndex + pageSize , count);
        List<T> page = allData.subList((int) fromIndex, (int) toIndex);
        PageResult<T> r = new PageResult<>();
        r.setList(page);
        r.setCount(count);
        r.setPageSize(pageSize);
        r.setCurrentPage(pageNum);
        return r;
    }

}
