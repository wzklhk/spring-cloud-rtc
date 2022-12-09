package com.example.common.pojo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * @author wzklhk
 */
@MappedSuperclass
@SQLDelete(sql = "update access_user set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public abstract class AbstractCommonLogicDeleteDO extends AbstractCommonDO {
    /**
     * 逻辑删除
     */
    @Column(name = "is_deleted", nullable = false,
            columnDefinition = "tinyint(1) DEFAULT 0 COMMENT '逻辑删除'")
    private Boolean isDeleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        AbstractCommonLogicDeleteDO that = (AbstractCommonLogicDeleteDO) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
