package com.example.access.admin.api.user.service;


import com.example.access.admin.pojo.user.User;
import com.example.access.admin.pojo.user.UserVO;
import com.example.common.api.service.CommonService;

/**
 * @author wzklhk
 */
public interface UserService extends CommonService<UserVO, User, Long> {

    UserVO getByToken(String token);

    UserVO getByUsername(String username);
}
