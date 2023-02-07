package com.example.service.rtc.api.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.common.api.service.impl.AbstractCommonLogicDeleteServiceJpaImpl;
import com.example.service.common.pojo.message.Message;
import com.example.service.common.pojo.message.MessageVO;
import com.example.service.rtc.api.message.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wzklhk
 */
@Service
public class MessageServiceImpl extends AbstractCommonLogicDeleteServiceJpaImpl<MessageVO, Message, Long>
        implements MessageService {
    @Override
    protected MessageVO toVO(Message entity) {
        MessageVO messageVO = super.toVO(entity);
        messageVO.setData(JSON.parse(entity.getData()));
        return messageVO;
    }

    @Override
    protected Message toDO(MessageVO entity) {
        Message message = super.toDO(entity);
        message.setData(JSON.toJSONString(entity.getData()));
        return message;
    }

    @Override
    protected Message toDO(Map<String, Object> entity) {
        Message message = super.toDO(entity);
        message.setData(JSON.toJSONString(entity.getOrDefault("data", null)));
        return message;
    }
}
