package com.example.service.signal.api.roomuser.repository;

import com.example.common.api.repository.CommonRepository;
import com.example.service.common.pojo.roomuser.RoomUser;
import org.springframework.stereotype.Repository;

/**
 * @author wzklhk
 */
@Repository
public interface RoomUserRepository extends CommonRepository<RoomUser, Long> {
}
