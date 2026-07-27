package com.optimagrowth.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Order(1)
public class TrackingFilter implements GlobalFilter {

    private static final Logger logger =
            LoggerFactory.getLogger(TrackingFilter.class);

    private final FilterUtils filterUtils;

    public TrackingFilter(FilterUtils filterUtils) {
        this.filterUtils = filterUtils;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        HttpHeaders headers = exchange.getRequest().getHeaders();

        String correlationId = filterUtils.getCorrelationId(headers);

        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();

            exchange = filterUtils.setCorrelationId(exchange, correlationId);

            logger.info("Generated Correlation ID: {}", correlationId);
        } else {
            logger.info("Existing Correlation ID: {}", correlationId);
        }
        ServerWebExchange mutatedExchange = exchange;

        if (correlationId == null || correlationId.isBlank()) {
            correlationId = generateCorrelationId();
            mutatedExchange = filterUtils.setCorrelationId(exchange, correlationId);
        }

        String finalCorrelationId = correlationId;
        ServerWebExchange finalExchange = mutatedExchange;

        finalExchange.getResponse().beforeCommit(() -> {
            finalExchange.getResponse()
                    .getHeaders()
                    .set(FilterUtils.CORRELATION_ID, finalCorrelationId);

            return Mono.empty();
        });

        return chain.filter(finalExchange);
    }

    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }
}