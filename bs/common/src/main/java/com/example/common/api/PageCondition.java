package com.example.common.api;

import lombok.Data;

@Data
public class PageCondition {

    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String sortBy;
    private String sortOrder = "asc";

}
