package com.example.access.admin.api.user.repository;


import com.example.access.admin.pojo.user.User;
import com.example.common.api.repository.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wzklhk
 */
@Repository
public interface UserRepository extends CommonRepository<User, Long> {
}
