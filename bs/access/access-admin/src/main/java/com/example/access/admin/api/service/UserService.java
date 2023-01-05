package com.example.access.admin.api.service;

import com.example.access.admin.pojo.entity.User;
import com.example.access.admin.pojo.vo.UserVO;
import com.example.common.api.service.CommonService;

/**
 * @author wzklhk
 */
public interface UserService extends CommonService<UserVO, User, Long> {

    UserVO getByToken(String token);

    UserVO getByUsername(String username);
}
