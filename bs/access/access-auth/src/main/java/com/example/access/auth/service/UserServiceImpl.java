package com.example.access.auth.service;

import com.example.access.auth.pojo.user.UserDO;
import com.example.access.auth.pojo.user.UserDTO;
import com.example.access.auth.pojo.user.UserDetailsImpl;
import com.example.access.auth.repository.UserRepository;
import com.example.common.api.service.impl.CommonServiceJpaImpl;
import com.example.common.utils.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends CommonServiceJpaImpl<UserDO, UserDO, Long> implements UserDetailsService {

    private List<UserDTO> userList;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO user = userRepository.getByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException("账户不存在");
        }
        UserDetailsImpl userDetails = CopyUtil.copy(user, UserDetailsImpl.class);
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        return userDetails;
    }
}
