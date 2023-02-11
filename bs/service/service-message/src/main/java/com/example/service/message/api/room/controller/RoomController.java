package com.example.service.message.api.room.controller;

import com.example.common.api.controller.CommonController;
import com.example.common.pojo.CommonResultInfo;
import com.example.service.common.pojo.room.Room;
import com.example.service.common.pojo.room.RoomVO;
import com.example.service.message.api.room.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wzklhk
 */
@RestController
@RequestMapping("/room")
public class RoomController extends CommonController<RoomVO, Room, Long> {

    @Autowired
    private RoomService roomService;

    @GetMapping("/getUserIds")
    public CommonResultInfo<List<Long>> getUsersByRoomId(@RequestParam Long id) {

        return CommonResultInfo.ok(roomService.getUserIdsByRoomId(id));
    }
}
