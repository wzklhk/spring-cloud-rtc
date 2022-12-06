package com.example.gatewaty.filter;

import com.example.gatewaty.properties.IgnoreUrlsProperties;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 白名单路径访问时需要移除JWT请求头
 *
 * @author wzklhk
 */
@Component
public class IgnoreUrlsRemoveJwtFilter implements WebFilter {

    private final IgnoreUrlsProperties ignoreUrlsProperties;

    public IgnoreUrlsRemoveJwtFilter(IgnoreUrlsProperties ignoreUrlsProperties) {
        this.ignoreUrlsProperties = ignoreUrlsProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();

        // 白名单路径移除JWT请求头
        for (String ignore : ignoreUrlsProperties.getUrls()) {
            if (pathMatcher.match(ignore, uri.getPath())) {
                request = exchange.getRequest().mutate().header("Authorization", "").build();
                exchange = exchange.mutate().request(request).build();
                return chain.filter(exchange);
            }
        }

        return chain.filter(exchange);
    }
}
