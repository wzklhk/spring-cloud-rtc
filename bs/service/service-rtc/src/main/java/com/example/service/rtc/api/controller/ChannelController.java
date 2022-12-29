package com.example.service.rtc.api.controller;

import com.example.common.api.controller.CommonController;
import com.example.service.common.pojo.channel.ChannelDO;
import com.example.service.common.pojo.channel.ChannelVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wzklhk
 */
@RestController
@RequestMapping("/channel")
public class ChannelController extends CommonController<ChannelVO, ChannelDO, Long> {
}
