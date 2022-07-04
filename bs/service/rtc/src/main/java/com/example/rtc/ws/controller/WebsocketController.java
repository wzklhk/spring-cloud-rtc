package com.example.rtc.ws.controller;

import com.example.common.api.ResultInfo;
import com.example.common.pojo.message.entity.Message;
import com.example.rtc.ws.service.WebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ws")
public class WebsocketController {

    @Value("${spring.cloud.client.ip-address}")
    private String ip;

    @Value("${server.port}")
    private Integer port;

    @Autowired
    private WebsocketService websocketService;

    @GetMapping("/getUrl")
    public ResultInfo getUrl() {
        String url = "ws://" + ip + ":" + port;
        return ResultInfo.ok(url);
    }

    @PostMapping("/unicast")
    public void unicast(@RequestBody Message message) {
        websocketService.unicastMessage(message.getReceiveUser(), message.getData());
    }

    @PostMapping("/multicast")
    public void multicast(@RequestBody Message message) {
        websocketService.multicastMessage(message.getReceiveUsers(), message.getData());
    }

    @PostMapping("/broadcast")
    public void broadcast(@RequestBody Message message) {
        websocketService.broadcastMessage(message.getData());
    }
}
