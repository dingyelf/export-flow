package io.github.exportflow.common;

import lombok.*;

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
        return count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
    }

    public boolean hasNextPage() {
        return currentPage < getTotalPage();
    }

}
