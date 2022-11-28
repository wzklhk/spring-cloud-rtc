package com.example.common.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author wzklhk
 */
@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractCommonLogicDeleteDO extends AbstractCommonDO {
    /**
     * 逻辑删除
     */
    @Column(name = "is_deleted", nullable = false,
            columnDefinition = "tinyint(1) DEFAULT 0 COMMENT '逻辑删除'")
    private Boolean isDeleted;
}
