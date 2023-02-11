package com.example.service.message.api.room.service;

import com.example.common.api.service.CommonService;
import com.example.service.common.pojo.room.Room;
import com.example.service.common.pojo.room.RoomVO;

import java.util.List;

/**
 * @author wzklhk
 */
public interface RoomService extends CommonService<RoomVO, Room, Long> {

    List<Long> getUserIdsByRoomId(Long id);

}
