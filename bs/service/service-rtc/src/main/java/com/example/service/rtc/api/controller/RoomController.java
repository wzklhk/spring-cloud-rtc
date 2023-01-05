package com.example.service.rtc.api.controller;

import com.example.common.api.controller.CommonController;
import com.example.service.common.pojo.room.Room;
import com.example.service.common.pojo.room.RoomVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wzklhk
 */
@RestController
@RequestMapping("/room")
public class RoomController extends CommonController<RoomVO, Room, Long> {
}
