package com.example.user_service.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

    public static final String CORRELATION_ID_HEADER =
            "tmx-correlation-id";

    public static final String CORRELATION_ID_MDC_KEY =
            "correlationId";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String correlationId =
                request.getHeader(CORRELATION_ID_HEADER);

        /*
         * Normally the Gateway creates it.
         * This fallback supports direct calls to User Service.
         */
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        try {
            MDC.put(CORRELATION_ID_MDC_KEY, correlationId);

            response.setHeader(
                    CORRELATION_ID_HEADER,
                    correlationId
            );

            filterChain.doFilter(request, response);

        } finally {
            /*
             * Important because servlet threads are reused.
             */
            MDC.remove(CORRELATION_ID_MDC_KEY);
        }
    }
}
