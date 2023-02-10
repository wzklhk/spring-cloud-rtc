package com.example.service.signal.api.ws.controller;

import com.example.common.pojo.CommonResultInfo;
import com.example.service.common.pojo.message.MessageVO;
import com.example.service.signal.api.ws.service.impl.WebSocketServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wzklhk
 */
@RestController
@RequestMapping("/ws")
public class WebsocketController {

    @Value("${spring.cloud.client.ip-address}")
    private String ip;

    @Value("${server.port}")
    private Integer port;

    private final WebSocketServiceImpl websocketService;

    public WebsocketController(WebSocketServiceImpl websocketService) {
        this.websocketService = websocketService;
    }

    @GetMapping("/getUrl")
    public CommonResultInfo getUrl() {
        String url = "ws://" + ip + ":" + port + "/ws";
        return CommonResultInfo.ok(url);
    }

    @GetMapping("/getConnectedUserList")
    public CommonResultInfo getConnectedUserList() {
        Map<Long, WebSocketServiceImpl> webSocketSessionMap = WebSocketServiceImpl.getWebSocketServiceMap();
        List<Long> userIds = new ArrayList<>();
        for (Map.Entry<Long, WebSocketServiceImpl> entry : webSocketSessionMap.entrySet()) {
            userIds.add(entry.getKey());
        }
        return CommonResultInfo.ok(userIds);
    }

    @PostMapping("/notifyUser")
    public <T> CommonResultInfo unicast(@RequestBody MessageVO<T> message) {
        websocketService.notifyAndSaveUserMessage(message.getReceiveUserId(), message.getData());
        return CommonResultInfo.ok();
    }

    @PostMapping("/notifyRoom")
    public <T> CommonResultInfo multicast(@RequestBody MessageVO<T> message) {
        websocketService.notifyAndSaveRoomMessage(message.getReceiveRoomId(), message.getData());
        return CommonResultInfo.ok();
    }
}
