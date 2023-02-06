package com.example.service.rtc.api.roomuser.service.impl;

import com.example.common.api.service.impl.AbstractCommonLogicDeleteServiceJpaImpl;
import com.example.service.common.pojo.roomuser.RoomUser;
import com.example.service.rtc.api.roomuser.service.RoomUserService;
import org.springframework.stereotype.Service;

/**
 * @author wzklhk
 */
@Service
public class RoomUserServiceImpl extends AbstractCommonLogicDeleteServiceJpaImpl<RoomUser, RoomUser, Long>
        implements RoomUserService {
}
