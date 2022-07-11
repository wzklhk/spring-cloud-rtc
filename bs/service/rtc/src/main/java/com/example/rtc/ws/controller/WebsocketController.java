package com.example.rtc.ws.controller;

import com.example.common.api.ResultInfo;
import com.example.common.pojo.message.entity.Message;
import com.example.rtc.ws.service.WebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    @GetMapping("/getConnectList")
    public ResultInfo getConnectList() {
        Set<WebsocketService> wsSet = WebsocketService.getWebSocketSet();
        return ResultInfo.ok(wsSet);
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
