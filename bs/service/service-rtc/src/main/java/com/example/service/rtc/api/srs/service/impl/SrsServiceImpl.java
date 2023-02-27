package com.example.service.rtc.api.srs.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.common.utils.CopyUtil;
import com.example.service.common.pojo.channel.ChannelVO;
import com.example.service.rtc.api.channel.service.ChannelService;
import com.example.service.rtc.api.srs.service.SrsService;
import com.example.service.rtc.feign.SrsFeignService;
import com.example.service.rtc.properties.SrsServerProperties;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wzklhk
 */
@Service
public class SrsServiceImpl implements SrsService {

    private final SrsFeignService srsFeignService;

    private final ChannelService channelService;

    private final SrsServerProperties srsServerProperties;

    public SrsServiceImpl(SrsServerProperties srsServerProperties, ChannelService channelService, SrsFeignService srsFeignService) {
        this.srsServerProperties = srsServerProperties;
        this.channelService = channelService;
        this.srsFeignService = srsFeignService;
    }

    @Override
    public JSONObject getVersion() {
        String url = srsServerProperties.getSchema() + "://" + srsServerProperties.getHost() + ":" + srsServerProperties.getPort()
                + "/api/v1/versions";
        return srsFeignService.getVersion();
    }

    @Override
    public JSONObject getStreams() {
        String url = srsServerProperties.getSchema() + "://" + srsServerProperties.getHost() + ":" + srsServerProperties.getPort()
                + "/api/v1/streams";
        return srsFeignService.getStreams();
    }

    @Override
    public JSONObject getStreamsById(String id) {
        String url = srsServerProperties.getSchema() + "://" + srsServerProperties.getHost() + ":" + srsServerProperties.getPort()
                + "/api/v1/streams"
                + "/" + id;
        return srsFeignService.getStreamsById(id);
    }

    @Override
    public JSONObject getClients() {
        String url = srsServerProperties.getSchema() + "://" + srsServerProperties.getHost() + ":" + srsServerProperties.getPort()
                + "/api/v1/clients";
        return srsFeignService.getClients();
    }

    @Override
    public JSONObject getClientsById(String id) {
        String url = srsServerProperties.getSchema() + "://" + srsServerProperties.getHost() + ":" + srsServerProperties.getPort()
                + "/api/v1/clients"
                + "/" + id;
        return srsFeignService.getClientsById(id);
    }

    @Override
    public JSONObject publish(String streamurl, String sdp) {
        String api = srsServerProperties.getSchema() + "://" + srsServerProperties.getHost() + ":" + srsServerProperties.getPort()
                + "/rtc/v1/publish/";

        Map<String, Object> params = new HashMap<>();
        params.put("api", api);
        params.put("streamurl", streamurl);
        params.put("sdp", sdp);
        params.put("tid", System.currentTimeMillis());

        ChannelVO channelVO = new ChannelVO();
        saveToChannel(channelVO);


        return srsFeignService.publish();
    }

    @Override
    public JSONObject publish(String room, String display, String sdp) {
        String api = srsServerProperties.getSchema() + "://" + srsServerProperties.getHost() + ":" + srsServerProperties.getPort()
                + "/rtc/v1/publish/";

        String streamurl = "webrtc://" + srsServerProperties.getHost()
                + "/" + room + "/" + display
                + "?secret=" + srsServerProperties.getSecret();

        Map<String, Object> params = new HashMap<>();
        params.put("api", api);
        params.put("streamurl", streamurl);
        params.put("sdp", sdp);
        params.put("tid", System.currentTimeMillis());

        return srsFeignService.publish();
    }

    @Override
    public JSONObject play(String streamurl, String sdp) {
        String api = srsServerProperties.getSchema() + "://" + srsServerProperties.getHost() + ":" + srsServerProperties.getPort()
                + "/rtc/v1/play/";

        Map<String, Object> params = new HashMap<>();
        params.put("api", api);
        params.put("streamurl", streamurl);
        params.put("sdp", sdp);
        params.put("tid", System.currentTimeMillis());

        return srsFeignService.play();

    }

    @Override
    public JSONObject play(String room, String display, String sdp) {
        String api = srsServerProperties.getSchema() + "://" + srsServerProperties.getHost() + ":" + srsServerProperties.getPort()
                + "/rtc/v1/play/";

        String streamurl = "webrtc://" + srsServerProperties.getHost()
                + "/" + room + "/" + display
                + "?secret=" + srsServerProperties.getSecret();

        Map<String, Object> params = new HashMap<>();
        params.put("api", api);
        params.put("streamurl", streamurl);
        params.put("sdp", sdp);
        params.put("tid", System.currentTimeMillis());

        return srsFeignService.play();
    }

    private void saveToChannel(ChannelVO saveChannel) {
        ChannelVO channelVO = new ChannelVO();
        channelVO.setStreamApp(saveChannel.getStreamApp());
        channelVO.setStreamName(saveChannel.getStreamName());

        List<ChannelVO> all = channelService.getAll(channelVO);

        ChannelVO channelVO1;
        if (all.size() > 0) {
            channelVO1 = all.get(0);
        }

        channelVO1 = CopyUtil.copy(saveChannel, ChannelVO.class);

        channelService.saveOrUpdate(channelVO1);

    }
}
