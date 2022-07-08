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

    private final WebsocketService websocketService;

    @Autowired
    public WebsocketController(WebsocketService websocketService) {
        this.websocketService = websocketService;
    }

    @GetMapping("/getUrl")
    public ResultInfo getUrl() {
        String url = "ws://" + ip + ":" + port;
        return ResultInfo.ok(url);
    }

    @PostMapping("/unicast")
    public <T> void unicast(@RequestBody Message<T> message) {
        websocketService.unicastMessage(message.getReceivers().get(0), message.getData());
    }

    @PostMapping("/multicast")
    public <T> void multicast(@RequestBody Message<T> message) {
        websocketService.multicastMessage(message.getReceivers(), message.getData());
    }

    @PostMapping("/broadcast")
    public void broadcast(@RequestBody Message message) {
        websocketService.broadcastMessage(message.getData());
    }

    @PostMapping("/notify")
    public void notify(@RequestBody Message message) {
        websocketService.broadcastMessage(message.getData());
    }
}
