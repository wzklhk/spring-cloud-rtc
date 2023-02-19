package com.example.common.pojo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author wzklhk
 */
@MappedSuperclass
@Getter
@Setter
@RequiredArgsConstructor
public abstract class AbstractCommonLogicDeleteDO extends AbstractCommonDO {
    /**
     * 逻辑删除
     */
    @Column(name = "is_deleted", nullable = false,
            columnDefinition = "tinyint(1) unsigned DEFAULT 0 COMMENT '逻辑删除'")
    private Boolean isDeleted;

}
