package com.example.service.rtc.feign;

import com.alibaba.fastjson.JSONObject;
import com.example.service.common.pojo.srs.SrsRtcQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wzklhk
 */
@FeignClient(name = "srs", url = "localhost:1985")
public interface SrsFeignService {

    @GetMapping("/api/v1/versions")
    JSONObject getVersion();

    @GetMapping("/api/v1/getVhosts")
    JSONObject getVhosts();


    @GetMapping("/api/v1/streams")
    JSONObject getStreams();

    @GetMapping("/api/v1/streams/{id}")
    JSONObject getStreamsById(@PathVariable("id") String id);

    @GetMapping("/api/v1/clients")
    JSONObject getClients();

    @GetMapping("/api/v1/clients/{id}")
    JSONObject getClientsById(@PathVariable("id") String id);

    @PostMapping("/rtc/v1/publish/")
    JSONObject publish(@RequestBody SrsRtcQuery srsRtcQuery);

    @PostMapping("/rtc/v1/play/")
    JSONObject play(@RequestBody SrsRtcQuery srsRtcQuery);

}
