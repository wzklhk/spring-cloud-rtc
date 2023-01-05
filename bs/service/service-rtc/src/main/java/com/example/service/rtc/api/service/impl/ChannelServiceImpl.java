package com.example.service.rtc.api.service.impl;

import com.example.common.api.service.impl.AbstractCommonLogicDeleteServiceJpaImpl;
import com.example.service.common.pojo.channel.Channel;
import com.example.service.common.pojo.channel.ChannelVO;
import com.example.service.rtc.api.service.ChannelService;
import org.springframework.stereotype.Service;

/**
 * @author wzklhk
 */
@Service
public class ChannelServiceImpl extends AbstractCommonLogicDeleteServiceJpaImpl<ChannelVO, Channel, Long>
        implements ChannelService {
}
