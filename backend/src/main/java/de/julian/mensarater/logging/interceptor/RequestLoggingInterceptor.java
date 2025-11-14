package de.julian.mensarater.logging.interceptor;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Object handler) {
        // Set correlation ID (from header or generate new)
        String correlationId = request.getHeader("X-Correlation-ID");
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }
        MDC.put("correlationId", correlationId);

        // Set user ID if available (e.g., from header, JWT, etc.)
        String userId = request.getHeader("X-User-ID");
        if (userId != null) {
            MDC.put("userId", userId);
        }

        logger.info("Incoming request: {} {} with query {}", request.getMethod(), request.getRequestURI(), request.getQueryString() != null ? request.getQueryString() : "-");

        return true;
    }

    @Override
    public void afterCompletion(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Object handler, Exception ex) {
        MDC.clear(); // Always clear MDC to avoid leaks
    }
}
