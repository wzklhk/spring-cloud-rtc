package com.example.service.rtc.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wzklhk
 */
@Data
@Component
@ConfigurationProperties(prefix = "srs")
public class SrsServerProperties {
    private String schema;

    private String host;

    private Integer port;

    private String secret;
}
