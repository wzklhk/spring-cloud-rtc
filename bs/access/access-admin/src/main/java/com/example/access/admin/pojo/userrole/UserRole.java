package com.example.access.admin.pojo.userrole;

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
@Table(name = "access_user_role")
@SQLDelete(sql = "update access_user_role set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserRole extends AbstractCommonLogicDeleteDO {

    @Column(nullable = false, unique = false,
            columnDefinition = "bigint(20) COMMENT '用户id'")
    private Long userId;

    @Column(nullable = false, unique = false,
            columnDefinition = "bigint(20) COMMENT '角色id'")
    private Long roleId;

}
