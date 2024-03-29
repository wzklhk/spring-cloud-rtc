package com.example.access.admin.api.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.access.admin.api.role.repository.RoleRepository;
import com.example.access.admin.api.user.repository.UserRepository;
import com.example.access.admin.api.user.service.UserService;
import com.example.access.admin.api.userrole.repository.UserRoleRepository;
import com.example.access.admin.feign.AuthFeignService;
import com.example.access.admin.pojo.role.Role;
import com.example.access.admin.pojo.role.RoleVO;
import com.example.access.admin.pojo.user.User;
import com.example.access.admin.pojo.user.UserVO;
import com.example.access.admin.pojo.userrole.UserRole;
import com.example.common.api.service.impl.AbstractCommonLogicDeleteServiceJpaImpl;
import com.example.common.pojo.CommonResultInfo;
import com.example.common.pojo.ErrorCodeEnum;
import com.example.common.utils.CopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author wzklhk
 */
@Slf4j
@Service
public class UserServiceImpl extends AbstractCommonLogicDeleteServiceJpaImpl<UserVO, User, Long>
        implements UserService {

    private final AuthFeignService authFeignService;

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final RoleRepository roleRepository;

    public UserServiceImpl(AuthFeignService authFeignService, UserRepository userRepository, UserRoleRepository userRoleRepository, RoleRepository roleRepository) {
        this.authFeignService = authFeignService;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleVO> getRolesByUserId(Long userId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        List<UserRole> all = userRoleRepository.findAll(Example.of(userRole));

        List<RoleVO> roles = new ArrayList<>();
        for (UserRole i : all) {
            Optional<Role> byId = roleRepository.findById(i.getRoleId());
            if (byId.isPresent()) {
                roles.add(CopyUtil.copy(byId.get(), RoleVO.class));
            }
        }
        return roles;
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
            log.error(res.getMsg());
            log.error(res.getData().toString());
            return null;
        }
        String username = res.getData().getString("user_name");
        return getByUsername(username);
    }

    @Override
    public UserVO getByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        List<User> all = userRepository.findAll(Example.of(user));
        if (all.size() > 0) {
            return CopyUtil.copy(all.get(0), UserVO.class);
        }
        return null;
    }
}
