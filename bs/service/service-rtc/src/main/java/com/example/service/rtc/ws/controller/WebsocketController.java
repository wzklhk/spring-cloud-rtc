package com.example.service.rtc.ws.controller;

import com.example.common.api.ResultInfo;
import com.example.service.common.pojo.message.entity.Message;
import com.example.service.common.pojo.user.entity.UserDO;
import com.example.service.rtc.ws.service.WebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public WebsocketController(WebsocketService websocketService) {
        this.websocketService = websocketService;
    }

    @GetMapping("/getUrl")
    public ResultInfo getUrl() {
        String url = "ws://" + ip + ":" + port + "/ws";
        return ResultInfo.ok(url);
    }

    @GetMapping("/getConnectedUserList")
    public ResultInfo getConnectedUserList() {
        Set<WebsocketService> wsSet = WebsocketService.getWebSocketSet();
        List<UserDO> users = new ArrayList<>();
        for (WebsocketService ws : wsSet) {
            users.add(ws.getCurrentUser());
        }
        return ResultInfo.ok(users);
    }

    @PostMapping("/unicast")
    public <T> ResultInfo unicast(@RequestBody Message<T> message) {
        websocketService.unicastMessage(message.getReceivers().get(0), message.getData());
        return ResultInfo.ok();
    }

    @PostMapping("/multicast")
    public <T> ResultInfo multicast(@RequestBody Message<T> message) {
        websocketService.multicastMessage(message.getReceivers(), message.getData());
        return ResultInfo.ok();
    }

    @PostMapping("/broadcast")
    public <T> ResultInfo broadcast(@RequestBody Message<T> message) {
        websocketService.broadcastMessage(message.getData());
        return ResultInfo.ok();
    }
}
