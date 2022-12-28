package com.example.service.rtc.api.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class SRSService {

    private final RestTemplate restTemplate;

    @Value("${srs.server-addr}")
    private String srsServer;

    public SRSService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public JSONObject getVersion() {
        String url = "http://" + srsServer + "/api/v1/versions";
        return restTemplate.getForObject(url, JSONObject.class);
    }

    public JSONObject getStreams() {
        String url = "http://" + srsServer + "/api/v1/streams";
        return restTemplate.getForObject(url, JSONObject.class);
    }

    public JSONObject getClients() {
        String url = "http://" + srsServer + "/api/v1/clients";
        return restTemplate.getForObject(url, JSONObject.class);
    }

    public JSONObject publish(String streamurl, String sdp) {
        String url = "http://" + srsServer + "/rtc/v1/publish/";

        Map<String, Object> params = new HashMap<>();
        params.put("api", url);
        params.put("streamurl", streamurl);
        params.put("sdp", sdp);

        return restTemplate.postForObject(url, params, JSONObject.class);
    }

    public JSONObject play(String streamurl, String sdp) {
        String url = "http://" + srsServer + "/rtc/v1/play/";

        Map<String, Object> params = new HashMap<>();
        params.put("api", url);
        params.put("streamurl", streamurl);
        params.put("sdp", sdp);

        return restTemplate.postForObject(url, params, JSONObject.class);
    }
}
