package com.msa.apigateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(Config.class);
    }

    /**
     * Filter Main
     */
    @Override
    public GatewayFilter apply(Config config) {
        // PreFilter
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if (config.isPostLogger()) {
                log.info("{} : Request IP = {} , Request Path = {}", config.message, request.getRemoteAddress(), request.getPath());
            }
            //PostFilter
            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> {
                        if (config.postLogger) {
                            log.info("{} : Response Status = {}", config.message, response.getStatusCode());
                        }
                    }));

        };
    }

    /**
     * Config의 경우 내가 Custom하게 설정하는 것
     * yml에서 Data를 읽어오기위해서는 Setter가 열려있어야 한다.
     */
    @Data
    public static class Config {
        boolean preLogger;
        boolean postLogger;
        String message;
    }

}

