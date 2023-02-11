package com.example.service.message.api.roomuser.service.impl;

import com.example.common.api.service.impl.AbstractCommonLogicDeleteServiceJpaImpl;
import com.example.service.common.pojo.roomuser.RoomUser;
import com.example.service.common.pojo.roomuser.RoomUserVO;
import com.example.service.message.api.roomuser.service.RoomUserService;
import org.springframework.stereotype.Service;

/**
 * @author wzklhk
 */
@Service
public class RoomUserServiceImpl extends AbstractCommonLogicDeleteServiceJpaImpl<RoomUserVO, RoomUser, Long>
        implements RoomUserService {
}
