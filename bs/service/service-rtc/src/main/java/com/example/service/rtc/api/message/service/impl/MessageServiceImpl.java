package com.example.service.rtc.api.message.service.impl;

import com.example.common.api.service.impl.AbstractCommonLogicDeleteServiceJpaImpl;
import com.example.service.common.pojo.message.Message;
import com.example.service.common.pojo.message.MessageVO;
import com.example.service.rtc.api.message.service.MessageService;
import org.springframework.stereotype.Service;

/**
 * @author wzklhk
 */
@Service
public class MessageServiceImpl extends AbstractCommonLogicDeleteServiceJpaImpl<MessageVO, Message, Long>
        implements MessageService {
}
