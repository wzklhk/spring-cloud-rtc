package com.example.access.auth.pojo.user;

import com.example.common.pojo.CommonDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "access_user")
@EqualsAndHashCode(callSuper = false)
@Data
public class UserDO extends CommonDO {
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
    private boolean enabled;

    /**
     * 是否锁定
     */
    private boolean locked;
}
