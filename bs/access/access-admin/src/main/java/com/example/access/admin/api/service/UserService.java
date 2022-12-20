package com.example.access.admin.api.service;

import com.example.access.admin.pojo.user.UserDO;
import com.example.access.admin.pojo.user.UserVO;
import com.example.common.api.service.CommonService;

/**
 * @author wzklhk
 */
public interface UserService extends CommonService<UserVO, UserDO, Long> {

    UserVO getByToken(String token);

    UserVO getByUsername(String username);
}
