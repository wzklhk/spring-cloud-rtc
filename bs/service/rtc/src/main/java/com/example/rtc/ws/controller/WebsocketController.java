package com.example.rtc.ws.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ws")
public class WebsocketController {

    @Value("${spring.cloud.client.ip-address}")
    private String ip;

    @Value("${server.port}")
    private Integer port;

    @GetMapping("getWsUrl")
    public String getWsUrl() {
        String url = "wss://" + ip + ":" + port;
        System.out.println(url);
        return url;
    }
}
