package com.example.service.rtc.user.repository;

import com.example.service.common.api.repository.CommonRepository;
import com.example.service.common.pojo.user.entity.UserDO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CommonRepository<UserDO, Integer> {
}
