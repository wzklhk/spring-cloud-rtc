package com.example.service.common.pojo.srs;

import lombok.Data;

/**
 * @author wzklhk
 */
@Data
public class SrsRtcQuery {
    private String api;

    private String streamurl;

    private String sdp;

    private Long tid;
}
