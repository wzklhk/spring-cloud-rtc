package com.example.service.rtc.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.common.pojo.CommonResultInfo;
import com.example.service.common.pojo.srs.PlayQuery;
import com.example.service.common.pojo.srs.PublishQuery;
import com.example.service.rtc.api.service.SRSService;
import org.springframework.web.bind.annotation.*;

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
        if (res.getIntValue("code") == 0) {
            return CommonResultInfo.ok(res);
        } else {
            return CommonResultInfo.status(res.getIntValue("code"), null, null);
        }
    }

    @GetMapping("/streams")
    public CommonResultInfo getStreams() {
        JSONObject res = srsService.getStreams();
        if (res.getIntValue("code") == 0) {
            return CommonResultInfo.ok(res);
        } else {
            return CommonResultInfo.status(res.getIntValue("code"), null, null);
        }
    }

    @GetMapping("/clients")
    public CommonResultInfo getClients() {
        JSONObject res = srsService.getClients();
        if (res.getIntValue("code") == 0) {
            return CommonResultInfo.ok(res);
        } else {
            return CommonResultInfo.status(res.getIntValue("code"), null, null);
        }
    }

    @PostMapping("/publish")
    public CommonResultInfo publish(@RequestBody PublishQuery query) {
        JSONObject res = srsService.publish(query.getStreamurl(), query.getSdp());
        if (res.getIntValue("code") == 0) {
            return CommonResultInfo.ok(res);
        } else {
            return CommonResultInfo.status(res.getIntValue("code"), null, null);
        }
    }

    @PostMapping("/play")
    public CommonResultInfo play(@RequestBody PlayQuery query) {
        JSONObject res = srsService.play(query.getStreamurl(), query.getSdp());
        if (res.getIntValue("code") == 0) {
            return CommonResultInfo.ok(res);
        } else {
            return CommonResultInfo.status(res.getIntValue("code"), null, null);
        }
    }
}
