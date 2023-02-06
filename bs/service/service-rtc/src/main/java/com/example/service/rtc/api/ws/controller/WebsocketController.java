package com.example.service.rtc.api.ws.controller;

import com.example.common.pojo.CommonResultInfo;
import com.example.service.common.pojo.message.MessageVO;
import com.example.service.common.pojo.user.UserVO;
import com.example.service.rtc.api.ws.service.WebsocketService;
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
        Map<UserVO, WebsocketService> webSocketSessionMap = WebsocketService.getWebSocketSessionMap();
        List<UserVO> users = new ArrayList<>();
        for (Map.Entry<UserVO, WebsocketService> entry : webSocketSessionMap.entrySet()) {
            users.add(entry.getKey());
        }
        return CommonResultInfo.ok(users);
    }

    @PostMapping("/unicast")
    public <T> CommonResultInfo unicast(@RequestBody MessageVO<T> messageVO) {
        websocketService.unicastMessage(messageVO.getReceivers().get(0), messageVO.getData());
        return CommonResultInfo.ok();
    }

    @PostMapping("/multicast")
    public <T> CommonResultInfo multicast(@RequestBody MessageVO<T> messageVO) {
        websocketService.multicastMessage(messageVO.getReceivers(), messageVO.getData());
        return CommonResultInfo.ok();
    }

    @PostMapping("/broadcast")
    public <T> CommonResultInfo broadcast(@RequestBody MessageVO<T> messageVO) {
        websocketService.broadcastMessage(messageVO.getData());
        return CommonResultInfo.ok();
    }
}
