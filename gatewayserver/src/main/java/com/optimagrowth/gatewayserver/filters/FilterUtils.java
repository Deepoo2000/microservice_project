package com.optimagrowth.gatewayserver.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class FilterUtils {

    public static final String CORRELATION_ID = "tmx-correlation-id";

    /**
     * Get Correlation ID from request headers.
     */
    public String getCorrelationId(HttpHeaders requestHeaders) {
        return requestHeaders.getFirst(CORRELATION_ID);
    }

    /**
     * Add Correlation ID to the request.
     */
    public ServerWebExchange setCorrelationId(ServerWebExchange exchange,
                                              String correlationId) {

        ServerHttpRequest request = exchange.getRequest()
                .mutate()
                .header(CORRELATION_ID, correlationId)
                .build();

        return exchange.mutate()
                .request(request)
                .build();
    }
}