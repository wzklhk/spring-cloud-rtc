package com.example.rtc.user.repository;

import com.example.common.api.repository.CommonRepository;
import com.example.common.pojo.user.entity.UserDO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CommonRepository<UserDO, Integer> {
}
