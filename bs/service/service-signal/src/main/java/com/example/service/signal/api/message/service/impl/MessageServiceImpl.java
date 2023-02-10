package com.example.service.signal.api.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.example.common.api.service.impl.AbstractCommonLogicDeleteServiceJpaImpl;
import com.example.service.common.pojo.message.Message;
import com.example.service.common.pojo.message.MessageVO;
import com.example.service.signal.api.message.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wzklhk
 */
@Slf4j
@Service
public class MessageServiceImpl extends AbstractCommonLogicDeleteServiceJpaImpl<MessageVO, Message, Long>
        implements MessageService {
    @Override
    protected MessageVO toVO(Message entity) {
        MessageVO messageVO = super.toVO(entity);
        String data = entity.getData();
        try {
            messageVO.setData(JSON.parse(data));
        } catch (JSONException e) {
            log.error(e.getMessage());
            messageVO.setData(data);
        }

        return messageVO;
    }

    @Override
    protected Message toDO(MessageVO entity) {
        Message message = super.toDO(entity);
        Object data = entity.getData();
        if (data != null) {
            message.setData(JSON.toJSONString(data));
        }
        return message;
    }

    @Override
    protected Message toDO(Map<String, Object> entity) {
        Message message = super.toDO(entity);
        Object data = entity.getOrDefault("data", null);
        if (data != null) {
            message.setData(JSON.toJSONString(data));
        }
        return message;
    }
}
