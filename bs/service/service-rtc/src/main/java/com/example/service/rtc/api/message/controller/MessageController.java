package com.example.service.rtc.api.message.controller;

import com.example.common.api.controller.CommonController;
import com.example.service.common.pojo.message.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wzklhk
 */
@RestController
@RequestMapping("/message")
public class MessageController extends CommonController<Message, Message, Long> {
}
