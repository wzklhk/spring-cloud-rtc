package com.example.gatewaty.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 网关白名单配置
 *
 * @author wzklhk
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@ConfigurationProperties(prefix = "auth.ignore")
public class IgnoreUrlsProperties {

    private List<String> urls;
}
