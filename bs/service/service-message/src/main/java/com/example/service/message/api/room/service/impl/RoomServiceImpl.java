package com.example.service.message.api.room.service.impl;

import com.example.common.api.service.impl.AbstractCommonLogicDeleteServiceJpaImpl;
import com.example.common.utils.CopyUtil;
import com.example.service.common.pojo.room.Room;
import com.example.service.common.pojo.room.RoomVO;
import com.example.service.common.pojo.roomuser.RoomUserVO;
import com.example.service.common.pojo.user.UserVO;
import com.example.service.message.access.feign.AdminFeignService;
import com.example.service.message.api.room.service.RoomService;
import com.example.service.message.api.roomuser.service.RoomUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wzklhk
 */
@Service
public class RoomServiceImpl extends AbstractCommonLogicDeleteServiceJpaImpl<RoomVO, Room, Long>
        implements RoomService {

    private final RoomUserService roomUserService;
    private final AdminFeignService adminFeignService;

    public RoomServiceImpl(RoomUserService roomUserService, AdminFeignService adminFeignService) {
        this.roomUserService = roomUserService;
        this.adminFeignService = adminFeignService;
    }

    @Override
    public List<Long> getUserIdsByRoomId(Long roomId) {
        RoomUserVO query = new RoomUserVO();
        query.setRoomId(roomId);

        List<Long> userIds = new ArrayList<>();
        for (RoomUserVO roomUser : roomUserService.getAll(query)) {
            userIds.add(roomUser.getUserId());
        }

        return userIds;
    }

    @Override
    protected RoomVO toVO(Room entity) {
        RoomVO roomVO = super.toVO(entity);
        UserVO user = CopyUtil.copy(adminFeignService.getUserById(entity.getCreateUserId()).getData(), UserVO.class);

        roomVO.setCreateUser(user);
        return roomVO;
    }
}
