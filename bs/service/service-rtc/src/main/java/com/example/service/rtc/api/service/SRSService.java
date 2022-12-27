package com.example.service.rtc.api.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SRSService {

    private final RestTemplate restTemplate;

    private static final String srsServer = "http://localhost:1985/";

    public SRSService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public JSONObject getVersion() {
        String url = srsServer + "/api/v1/versions";
        return restTemplate.getForObject(url, JSONObject.class);
    }

    public JSONObject getStreams() {
        String url = srsServer + "/api/v1/streams";
        return restTemplate.getForObject(url, JSONObject.class);
    }

    public JSONObject getClients() {
        String url = srsServer + "/api/v1/clients";
        return restTemplate.getForObject(url, JSONObject.class);
    }

    public JSONObject publish() {
        String url = srsServer + "/rtc/v1/publish";
        return restTemplate.postForObject(url, null, JSONObject.class);
    }

    public JSONObject play() {
        String url = srsServer + "/rtc/v1/play";
        return restTemplate.postForObject(url, null, JSONObject.class);
    }
}
