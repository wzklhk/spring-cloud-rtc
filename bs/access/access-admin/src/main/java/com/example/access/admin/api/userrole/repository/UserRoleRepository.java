package com.example.access.admin.api.userrole.repository;

import com.example.access.admin.pojo.userrole.UserRole;
import com.example.common.api.repository.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wzklhk
 */
@Repository
public interface UserRoleRepository extends CommonRepository<UserRole, Long> {
}
