package com.example.access.admin.api.role.repository;

import com.example.access.admin.pojo.role.Role;
import com.example.common.api.repository.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wzklhk
 */
@Repository
public interface RoleRepository extends CommonRepository<Role, Long> {
}
