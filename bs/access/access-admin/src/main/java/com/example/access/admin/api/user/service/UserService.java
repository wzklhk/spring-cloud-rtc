package com.example.access.admin.api.user.service;


import com.example.access.admin.pojo.role.RoleVO;
import com.example.access.admin.pojo.user.User;
import com.example.access.admin.pojo.user.UserVO;
import com.example.common.api.service.CommonService;

import java.util.List;

/**
 * @author wzklhk
 */
public interface UserService extends CommonService<UserVO, User, Long> {

    List<RoleVO> getRolesByUserId(Long userId);

    UserVO getByToken(String token);

    UserVO getByUsername(String username);
}
