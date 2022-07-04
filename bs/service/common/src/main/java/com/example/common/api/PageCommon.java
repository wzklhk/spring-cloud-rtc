package com.example.common.api;

import com.example.common.utils.CopyUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageCommon<T> {

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页面大小
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 排列字段
     */
    private String sortBy;

    /**
     * 排列方式
     */
    private String sortOrder;

    /**
     * 分页数据
     */
    private List<T> list;

    public static <T> PageCommon<T> of(Page<T> page) {
        PageCommon<T> result = new PageCommon<T>();
        result.setPageNum(page.getNumber() + 1);
        result.setPageSize(page.getSize());
        result.setTotalPage(page.getTotalPages());
        result.setTotal(page.getTotalElements());
        result.setList(page.getContent());
        return result;
    }

    public static <T> PageCommon<T> of(Page page, Class<T> entityModelClass) {
        PageCommon<T> result = new PageCommon<T>();
        result.setPageNum(page.getNumber() + 1);
        result.setPageSize(page.getSize());
        result.setTotalPage(page.getTotalPages());
        result.setTotal(page.getTotalElements());
        result.setList(CopyUtil.copyList(page.getContent(), entityModelClass));
        return result;
    }
}
