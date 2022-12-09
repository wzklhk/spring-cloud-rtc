package com.example.access.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.access.admin.feign.AuthFeignService;
import com.example.access.admin.pojo.user.UserDO;
import com.example.access.admin.pojo.user.UserVO;
import com.example.access.admin.repository.UserRepository;
import com.example.access.admin.service.UserService;
import com.example.common.api.service.impl.AbstractCommonLogicDeleteServiceJpaImpl;
import com.example.common.utils.CopyUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wzklhk
 */
@Service
public class UserServiceImpl extends AbstractCommonLogicDeleteServiceJpaImpl<UserVO, UserDO, Long>
        implements UserService {

    private final AuthFeignService authFeignService;

    private final UserRepository userRepository;

    public UserServiceImpl(AuthFeignService authFeignService, UserRepository userRepository) {
        this.authFeignService = authFeignService;
        this.userRepository = userRepository;
    }


    @Override
    public UserVO saveOrUpdate(Map<String, Object> queryMap) {
        if (!queryMap.containsKey("id")) {
            if (!queryMap.containsKey("isEnabled")) {
                queryMap.put("isEnabled", true);
            }
            if (!queryMap.containsKey("isLocked")) {
                queryMap.put("isLocked", true);
            }
        }
        return super.saveOrUpdate(queryMap);
    }

    @Override
    public UserVO getByToken(String token) {
        JSONObject jsonObject = authFeignService.checkToken(token);
        String username = jsonObject.getString("user_name");
        UserDO userDO = userRepository.getByUsername(username);
        return CopyUtil.copy(userDO, UserVO.class);
    }
}
