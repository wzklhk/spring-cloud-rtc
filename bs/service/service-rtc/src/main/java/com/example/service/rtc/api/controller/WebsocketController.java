package com.example.service.rtc.api.controller;

import com.example.common.pojo.CommonResultInfo;
import com.example.service.common.pojo.message.Message;
import com.example.service.common.pojo.user.UserVO;
import com.example.service.rtc.api.service.WebsocketService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        Set<WebsocketService> wsSet = WebsocketService.getWebSocketSet();
        List<UserVO> users = new ArrayList<>();
        for (WebsocketService ws : wsSet) {
            users.add(ws.getCurrentUser());
        }
        return CommonResultInfo.ok(users);
    }

    @PostMapping("/unicast")
    public <T> CommonResultInfo unicast(@RequestBody Message<T> message) {
        websocketService.unicastMessage(message.getReceivers().get(0), message.getData());
        return CommonResultInfo.ok();
    }

    @PostMapping("/multicast")
    public <T> CommonResultInfo multicast(@RequestBody Message<T> message) {
        websocketService.multicastMessage(message.getReceivers(), message.getData());
        return CommonResultInfo.ok();
    }

    @PostMapping("/broadcast")
    public <T> CommonResultInfo broadcast(@RequestBody Message<T> message) {
        websocketService.broadcastMessage(message.getData());
        return CommonResultInfo.ok();
    }
}
