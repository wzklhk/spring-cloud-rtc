package com.example.access.auth.repository;

import com.example.access.auth.pojo.user.UserDO;
import com.example.common.api.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CommonRepository<UserDO, Long> {
}
