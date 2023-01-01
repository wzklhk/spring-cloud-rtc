package com.example.service.common.pojo.srs;

import lombok.Data;

@Data
public class PlayQuery {
    private String streamurl;

    private String room;

    private String display;

    private String sdp;
}
