package com.example.service.rtc.api.srs.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author wzklhk
 */
public interface SrsService {
    JSONObject getVersion();

    JSONObject getStreams();

    JSONObject getStreamsById(String id);

    JSONObject getClients();

    JSONObject getClientsById(String id);

    JSONObject publish(String streamurl, String sdp);

    JSONObject publish(String room, String display, String sdp);

    JSONObject play(String streamurl, String sdp);

    JSONObject play(String room, String display, String sdp);
}
