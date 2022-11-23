package com.example.access.admin.repository;

import com.example.access.admin.pojo.user.UserDO;
import com.example.common.api.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CommonRepository<UserDO, Long> {

    UserDO getByUsername(String username);
}
