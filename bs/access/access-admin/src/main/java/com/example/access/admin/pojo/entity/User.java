package com.example.access.admin.pojo.entity;

import com.example.common.pojo.AbstractCommonLogicDeleteDO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 注解@SQLDelete(sql = "update access_user set is_deleted = 1 where id = ?")
 * 注解@Where(clause = "is_deleted = 0")
 * 用于开启逻辑删除
 *
 * @author wzklhk
 */
@Entity
@Table(name = "access_user")
@SQLDelete(sql = "update access_user set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User extends AbstractCommonLogicDeleteDO {

    /**
     * 用户名
     */
    @Column(nullable = false, unique = true,
            columnDefinition = "varchar(255) COMMENT '用户名'")
    private String username;

    /**
     * 密码
     */
    @Column(nullable = false,
            columnDefinition = "varchar(255) DEFAULT 12345678 COMMENT '密码'")
    private String password;

    /**
     * 是否可用
     */
    @Column(name = "is_enabled", nullable = false,
            columnDefinition = "tinyint(1) DEFAULT 1 COMMENT '是否可用，1为true，0为false'")
    private Boolean isEnabled;

    /**
     * 是否锁定
     */
    @Column(name = "is_locked", nullable = false,
            columnDefinition = "tinyint(1) DEFAULT 0 COMMENT '是否锁定，1为true，0为false'")
    private Boolean isLocked;

}
