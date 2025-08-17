package com.raksa.app.config.filters;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.UUID;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AddResponseHeaderWebFilter implements WebFilter, Ordered {

    private final Tracer tracer;

    @SuppressWarnings("NullableProblems")
    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
        return chain.filter(withCallback(exchange));
    }

    private ServerWebExchange withCallback(final ServerWebExchange exchange) {
        exchange
                .getResponse()
                .beforeCommit(() -> {
                    HttpHeaders httpHeaders = exchange.getResponse().getHeaders();

                    httpHeaders.add("trace-id", callId());

                    return Mono.empty();
                });

        return exchange;
    }

    private static String fallbackValue() {
        String fallbackValue = UUID.randomUUID().toString();
        log.warn("Could not use trace ID; generated dummy UUID for tracking purposes: {}", fallbackValue);

        return fallbackValue;
    }

    private String callId() {
        Span currentSpan = this.tracer.currentSpan();

        return currentSpan == null
                ? fallbackValue()
                : currentSpan.context().traceId();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}