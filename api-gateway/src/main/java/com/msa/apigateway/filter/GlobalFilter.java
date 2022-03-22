package com.msa.apigateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if (config.isPreFilter()) {
                log.info("Hi I'm {}", config.message);
            }

            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                                if (config.isPostFilter())
                                    log.info("Hi I'm {}", config.message);
                            }
                    ));
        }));
    }

    @Data
    public static class Config {
        private String message;
        private boolean preFilter;
        private boolean postFilter;
    }
}
