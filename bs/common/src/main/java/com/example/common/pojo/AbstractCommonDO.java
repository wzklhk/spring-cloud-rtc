package com.example.common.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 注解@EntityListeners(value = AuditingEntityListener.class)  // 自动修改createTime和updateTime
 *
 * @author wzklhk
 */
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public abstract class AbstractCommonDO implements Serializable {

    /**
     * 数据库主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true,
            columnDefinition = "bigint(20) COMMENT '主键'")
    private Long id;

    /**
     * 创建时间
     * <p>
     * 注解@CreatedDate  // 自动修改createTime
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreatedDate
    @Column(nullable = false,
            columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    private Date createTime;

    /**
     * 更新时间
     * <p>
     * 注解@LastModifiedDate  // 自动修改updateTime
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @LastModifiedDate
    @Column(nullable = false,
            columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'")
    private Date updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        AbstractCommonDO that = (AbstractCommonDO) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
