package com.example.service.common.pojo.srs;

import lombok.Data;

@Data
public class PublishQuery {
    private String streamurl;

    private String room;

    private String display;

    private String sdp;
}
