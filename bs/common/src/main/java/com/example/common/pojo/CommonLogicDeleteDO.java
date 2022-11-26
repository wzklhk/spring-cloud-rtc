package com.example.common.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class CommonLogicDeleteDO extends CommonDO {
    /**
     * 逻辑删除
     */
    private Boolean deleted;
}
