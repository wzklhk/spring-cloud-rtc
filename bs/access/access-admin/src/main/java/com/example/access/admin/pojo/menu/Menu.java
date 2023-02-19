package com.example.access.admin.pojo.menu;

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
 * @author wzklhk
 */

@Entity
@Table(name = "access_menu")
@SQLDelete(sql = "update access_menu set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Menu extends AbstractCommonLogicDeleteDO {

    @Column(nullable = false, unique = true,
            columnDefinition = "varchar(255) COMMENT '菜单名'")
    private String name;

    @Column(nullable = false, unique = true,
            columnDefinition = "varchar(255) COMMENT '菜单路径'")
    private String path;
}
