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
    public UserVO toVO(UserDO entity) {
        return CopyUtil.copy(entity, UserVO.class);
    }

    @Override
    public UserDO toDO(UserVO entity) {
        UserDO result = CopyUtil.copy(entity, UserDO.class);
        if (result.getId() == null) {
            if (result.getPassword() == null) {
                result.setPassword("12345678");
            }
            if (result.getIsEnabled() == null) {
                result.setIsEnabled(true);
            }
            if (result.getIsLocked() == null) {
                result.setIsLocked(false);
            }
            if (result.getIsDeleted() == null) {
                result.setIsDeleted(false);
            }
        }
        return result;
    }

    @Override
    public UserDO toDO(Map<String, Object> queryMap) {
        UserDO result = CopyUtil.copy(queryMap, UserDO.class);
        if (result.getId() == null) {
            if (result.getPassword() == null) {
                result.setPassword("12345678");
            }
            if (result.getIsEnabled() == null) {
                result.setIsEnabled(true);
            }
            if (result.getIsLocked() == null) {
                result.setIsLocked(false);
            }
            if (result.getIsDeleted() == null) {
                result.setIsDeleted(false);
            }
        }
        return result;
    }

    @Override
    public UserVO getByToken(String token) {
        JSONObject jsonObject = authFeignService.checkToken(token);
        String username = jsonObject.getString("user_name");
        UserDO userDO = userRepository.getByUsername(username);
        return toVO(userDO);
    }
}
