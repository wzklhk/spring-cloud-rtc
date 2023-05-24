package com.example.service.rtc.api.srs;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * @author wzklhk
 */
@Data
public class HookDvrQuery {
    private String action;
    @JsonAlias("client_id")
    private String clientId;
    private String ip;
    private String vhost;
    private String app;
    private String stream;
    private String cwd;
    private String file;

}
