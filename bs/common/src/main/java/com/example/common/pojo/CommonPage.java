package com.example.common.pojo;

import com.example.common.utils.CopyUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author wzklhk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonPage<T> {

    @ApiModelProperty(value = "页码")
    private Integer pageNum;
    public static final String PAGE_NUM = "pageNum";

    @ApiModelProperty(value = "页面大小")
    private Integer pageSize;
    public static final String PAGE_SIZE = "pageSize";

    @ApiModelProperty(value = "总页数")
    private Integer totalPage;
    public static final String TOTAL_PAGE = "totalPage";

    @ApiModelProperty(value = "总条数")
    private Long total;
    public static final String TOTAL = "total";

    @ApiModelProperty(value = "排列字段")
    private String sortBy;
    public static final String SORT_BY = "sortBy";

    @ApiModelProperty(value = "排列方式")
    private String sortOrder;
    public static final String SORT_ORDER = "sortOrder";

    @ApiModelProperty(value = "分页数据")
    private List<T> list;
    public static final String LIST = "list";

    public static <T> CommonPage<T> of(Page<T> page) {
        CommonPage<T> result = new CommonPage<T>();
        result.setPageNum(page.getNumber() + 1);
        result.setPageSize(page.getSize());
        result.setTotalPage(page.getTotalPages());
        result.setTotal(page.getTotalElements());
        result.setSortBy(page.getSort().stream().iterator().next().getProperty());
        result.setSortOrder(page.getSort().stream().iterator().next().getDirection().toString());
        result.setList(page.getContent());
        return result;
    }

    public static <T> CommonPage<T> of(Page page, Class<T> entityModelClass) {
        CommonPage<T> result = new CommonPage<>();
        // 设置页面基本信息
        result.setPageNum(page.getNumber() + 1);
        result.setPageSize(page.getSize());
        result.setTotalPage(page.getTotalPages());
        result.setTotal(page.getTotalElements());
        result.setSortBy(page.getSort().stream().iterator().next().getProperty());
        result.setSortOrder(page.getSort().stream().iterator().next().getDirection().toString());
        // 设置页面数据
        result.setList(CopyUtil.copyList(page.getContent(), entityModelClass));
        return result;
    }
}
