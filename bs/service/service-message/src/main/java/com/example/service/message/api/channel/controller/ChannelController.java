package com.example.service.message.api.channel.controller;

import com.example.common.api.controller.CommonController;
import com.example.service.common.pojo.channel.Channel;
import com.example.service.common.pojo.channel.ChannelVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wzklhk
 */
@RestController
@RequestMapping("/channel")
public class ChannelController extends CommonController<ChannelVO, Channel, Long> {
}
