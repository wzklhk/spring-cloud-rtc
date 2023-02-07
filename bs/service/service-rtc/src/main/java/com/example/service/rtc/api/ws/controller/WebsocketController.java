package com.example.service.rtc.api.ws.controller;

import com.example.common.pojo.CommonResultInfo;
import com.example.service.rtc.api.ws.service.WebsocketService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final WebsocketService websocketService;

    public WebsocketController(WebsocketService websocketService) {
        this.websocketService = websocketService;
    }

    @GetMapping("/getUrl")
    public CommonResultInfo getUrl() {
        String url = "ws://" + ip + ":" + port + "/ws";
        return CommonResultInfo.ok(url);
    }

    @GetMapping("/getConnectedUserList")
    public CommonResultInfo getConnectedUserList() {
        Map<Long, WebsocketService> webSocketSessionMap = WebsocketService.getWebSocketServiceMap();
        List<Long> userIds = new ArrayList<>();
        for (Map.Entry<Long, WebsocketService> entry : webSocketSessionMap.entrySet()) {
            userIds.add(entry.getKey());
        }
        return CommonResultInfo.ok(userIds);
    }

    /*@PostMapping("/unicast")
    public <T> CommonResultInfo unicast(@RequestBody MessageWsVO<T> messageWsVO) {
        websocketService.unicastMessage(messageWsVO.getReceiverIds().get(0), messageWsVO.getData());
        return CommonResultInfo.ok();
    }

    @PostMapping("/multicast")
    public <T> CommonResultInfo multicast(@RequestBody MessageWsVO<T> messageWsVO) {
        websocketService.multicastMessage(messageWsVO.getReceiverId(), messageWsVO.getData());
        return CommonResultInfo.ok();
    }

    @PostMapping("/broadcast")
    public <T> CommonResultInfo broadcast(@RequestBody MessageWsVO<T> messageWsVO) {
        websocketService.broadcastMessage(messageWsVO.getData());
        return CommonResultInfo.ok();
    }

    @PostMapping("/notification")
    public <T> CommonResultInfo notification(@RequestBody MessageWsVO<T> messageWsVO) {
        websocketService.notifyMessage(messageWsVO.getReceiverIds(), messageWsVO.getData());
        return CommonResultInfo.ok();
    }*/
}
