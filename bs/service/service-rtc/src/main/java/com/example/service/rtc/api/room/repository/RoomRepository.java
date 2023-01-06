package com.example.service.rtc.api.room.repository;

import com.example.common.api.repository.CommonRepository;
import com.example.service.common.pojo.room.Room;
import org.springframework.stereotype.Repository;

/**
 * @author wzklhk
 */
@Repository
public interface RoomRepository extends CommonRepository<Room, Long> {
}
