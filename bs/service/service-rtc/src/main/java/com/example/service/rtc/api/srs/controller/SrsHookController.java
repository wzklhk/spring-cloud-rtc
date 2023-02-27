package com.example.service.rtc.api.srs.controller;

import com.example.common.pojo.CommonResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author wzklhk
 */
@Slf4j
@RestController
@RequestMapping("/srs/hook")
public class SrsHookController {

    @RequestMapping("/onPublish")
    public CommonResultInfo onPublish(@RequestBody Map<String, Object> queryMap) {
        System.out.println("onPublish");
        log.debug(queryMap.toString());

        return CommonResultInfo.ok();
    }

    @RequestMapping("/onUnpublish")
    public CommonResultInfo onUnpublish(@RequestBody Map<String, Object> queryMap) {
        System.out.println("onUnpublish");
        log.debug(queryMap.toString());

        return CommonResultInfo.ok();
    }

    @RequestMapping("/onPlay")
    public CommonResultInfo onPlay(@RequestBody Map<String, Object> queryMap) {
        System.out.println("onPlay");
        log.debug(queryMap.toString());

        return CommonResultInfo.ok();
    }

    @RequestMapping("/onStop")
    public CommonResultInfo onStop(@RequestBody Map<String, Object> queryMap) {
        System.out.println("onStop");
        log.debug(queryMap.toString());

        return CommonResultInfo.ok();
    }
}
