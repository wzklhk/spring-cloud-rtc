package com.example.access.admin.pojo.user;

import lombok.Data;

/**
 * @author wzklhk
 */
@Data
public class UserVO {
    private Long id;
    private String username;
    private String password;
    private Boolean isEnabled;
    private Boolean isLocked;
}
