package com.example.access.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.access.admin.feign.AuthFeignService;
import com.example.access.admin.pojo.user.UserDO;
import com.example.access.admin.pojo.user.UserVO;
import com.example.access.admin.repository.UserRepository;
import com.example.access.admin.service.UserService;
import com.example.common.api.service.impl.AbstractCommonLogicDeleteServiceJpaImpl;
import com.example.common.pojo.CommonResultInfo;
import com.example.common.pojo.ErrorCodeEnum;
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
    public UserVO saveOrUpdate(Map<String, Object> query) {
        if (!query.containsKey("id")) {
            if (!query.containsKey("password")) {
                query.put("password", "12345678");
            }
            if (!query.containsKey("isEnabled")) {
                query.put("isEnabled", true);
            }
            if (!query.containsKey("isLocked")) {
                query.put("isLocked", false);
            }
        }
        return super.saveOrUpdate(query);
    }

    @Override
    public UserVO getByToken(String token) {
        CommonResultInfo<JSONObject> res = authFeignService.checkToken(token);
        if (!res.getCode().equals(ErrorCodeEnum.OK.getErrorCode())) {
            return null;
        }
        String username = res.getData().getString("user_name");
        UserDO userDO = userRepository.getByUsername(username);
        return CopyUtil.copy(userDO, UserVO.class);
    }
}
