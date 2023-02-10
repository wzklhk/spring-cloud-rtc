package com.example.service.signal.api.srs.service;

import com.alibaba.fastjson.JSONObject;
import com.example.service.signal.properties.SRSServerProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wzklhk
 */
@Service
public class SRSService {

    private final RestTemplate restTemplate;

    private final SRSServerProperties srsServerProperties;

    public SRSService(RestTemplate restTemplate, SRSServerProperties srsServerProperties) {
        this.restTemplate = restTemplate;
        this.srsServerProperties = srsServerProperties;
    }

    public JSONObject getVersion() {
        String url = srsServerProperties.getSchema() + "://" + srsServerProperties.getHost() + ":" + srsServerProperties.getPort() + "/api/v1/versions";
        return restTemplate.getForObject(url, JSONObject.class);
    }

    public JSONObject getStreams() {
        String url = srsServerProperties.getSchema() + "://" + srsServerProperties.getHost() + ":" + srsServerProperties.getPort() + "/api/v1/streams";
        return restTemplate.getForObject(url, JSONObject.class);
    }

    public JSONObject getClients() {
        String url = srsServerProperties.getSchema() + "://" + srsServerProperties.getHost() + ":" + srsServerProperties.getPort() + "/api/v1/clients";
        return restTemplate.getForObject(url, JSONObject.class);
    }

    public JSONObject publish(String streamurl, String sdp) {
        String api = srsServerProperties.getSchema() + "://" + srsServerProperties.getHost() + ":" + srsServerProperties.getPort() + "/rtc/v1/publish/";

        Map<String, Object> params = new HashMap<>();
        params.put("api", api);
        params.put("streamurl", streamurl);
        params.put("sdp", sdp);
        params.put("tid", new Date().getTime());

        return restTemplate.postForObject(api, params, JSONObject.class);
    }

    public JSONObject publish(String room, String display, String sdp) {
        String api = srsServerProperties.getSchema() + "://" + srsServerProperties.getHost() + ":" + srsServerProperties.getPort() + "/rtc/v1/publish/";
        String streamurl = "webrtc://" + srsServerProperties.getHost() + "/" + room + "/" + display;

        Map<String, Object> params = new HashMap<>();
        params.put("api", api);
        params.put("streamurl", streamurl);
        params.put("sdp", sdp);
        params.put("tid", new Date().getTime());

        return restTemplate.postForObject(api, params, JSONObject.class);
    }

    public JSONObject play(String streamurl, String sdp) {
        String api = srsServerProperties.getSchema() + "://" + srsServerProperties.getHost() + ":" + srsServerProperties.getPort() + "/rtc/v1/play/";

        Map<String, Object> params = new HashMap<>();
        params.put("api", api);
        params.put("streamurl", streamurl);
        params.put("sdp", sdp);
        params.put("tid", new Date().getTime());

        return restTemplate.postForObject(api, params, JSONObject.class);
    }

    public JSONObject play(String room, String display, String sdp) {
        String api = srsServerProperties.getSchema() + "://" + srsServerProperties.getHost() + ":" + srsServerProperties.getPort() + "/rtc/v1/play/";
        String streamurl = "webrtc://" + srsServerProperties.getHost() + "/" + room + "/" + display;

        Map<String, Object> params = new HashMap<>();
        params.put("api", api);
        params.put("streamurl", streamurl);
        params.put("sdp", sdp);
        params.put("tid", new Date().getTime());

        return restTemplate.postForObject(api, params, JSONObject.class);
    }
}
