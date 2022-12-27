package com.example.service.rtc.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.common.pojo.CommonResultInfo;
import com.example.service.rtc.api.service.SRSService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/srs")
public class SRSController {

    private final SRSService srsService;

    public SRSController(SRSService srsService) {
        this.srsService = srsService;
    }

    @GetMapping("/versions")
    public CommonResultInfo getVersions() {
        JSONObject res = srsService.getVersion();
        return CommonResultInfo.status(res.getIntValue("code"), res.getString("server"), res.getJSONObject("data"));
    }

    @GetMapping("/streams")
    public CommonResultInfo getStreams() {
        JSONObject res = srsService.getStreams();
        return CommonResultInfo.status(res.getIntValue("code"), res.getString("server"), res.getJSONObject("data"));
    }

    @GetMapping("/clients")
    public CommonResultInfo getClients() {
        JSONObject res = srsService.getClients();
        return CommonResultInfo.status(res.getIntValue("code"), res.getString("server"), res.getJSONObject("data"));
    }

    @PostMapping("/publish")
    public CommonResultInfo publish() {
        JSONObject res = srsService.publish();
        return CommonResultInfo.status(res.getIntValue("code"), res.getString("server"), res.getJSONObject("data"));
    }

    @PostMapping("/play")
    public CommonResultInfo play() {
        JSONObject res = srsService.play();
        return CommonResultInfo.status(res.getIntValue("code"), res.getString("server"), res.getJSONObject("data"));
    }
}
