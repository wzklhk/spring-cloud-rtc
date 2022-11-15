package com.example.common.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageQuery {

    @ApiModelProperty(value = "页码，从1开始")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "页面大小，默认为10")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "排序字段")
    private String sortBy;

    @ApiModelProperty(value = "排序方式，默认升序")
    private String sortOrder = "asc";

}
