package com.example.access.admin.pojo.user;

import com.example.common.pojo.CommonLogicDeleteDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "access_user")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDO extends CommonLogicDeleteDO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 是否可用
     */
    private Boolean enabled;

    /**
     * 是否锁定
     */
    private Boolean locked;
}
