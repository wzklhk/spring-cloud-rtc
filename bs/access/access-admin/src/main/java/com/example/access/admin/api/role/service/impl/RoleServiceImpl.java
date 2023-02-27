package com.example.access.admin.api.role.service.impl;

import com.example.access.admin.api.role.repository.RoleRepository;
import com.example.access.admin.api.role.service.RoleService;
import com.example.access.admin.api.user.repository.UserRepository;
import com.example.access.admin.api.userrole.repository.UserRoleRepository;
import com.example.access.admin.pojo.role.Role;
import com.example.access.admin.pojo.role.RoleVO;
import com.example.access.admin.pojo.user.User;
import com.example.access.admin.pojo.user.UserVO;
import com.example.access.admin.pojo.userrole.UserRole;
import com.example.common.api.service.impl.AbstractCommonLogicDeleteServiceJpaImpl;
import com.example.common.utils.CopyUtil;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author wzklhk
 */
@Service
public class RoleServiceImpl extends AbstractCommonLogicDeleteServiceJpaImpl<RoleVO, Role, Long>
        implements RoleService {

    private final RoleRepository roleRepository;

    private final UserRoleRepository userRoleRepository;

    private final UserRepository userRepository;

    public RoleServiceImpl(UserRoleRepository userRoleRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public List<UserVO> getUsersByRoleId(Long roleId) {
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleId);
        List<UserRole> all = userRoleRepository.findAll(Example.of(userRole));

        List<UserVO> users = new ArrayList<>();
        for (UserRole i : all) {
            Optional<User> byId = userRepository.findById(i.getUserId());
            if (byId.isPresent()) {
                users.add(CopyUtil.copy(byId.get(), UserVO.class));
            }
        }
        return users;
    }

    public void test() {
        roleRepository.findAll();
    }
}
