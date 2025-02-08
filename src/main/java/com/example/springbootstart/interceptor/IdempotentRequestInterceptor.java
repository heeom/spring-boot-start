package com.example.springbootstart.interceptor;

import com.example.springbootstart.exception.DuplicateRequestException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Component
public class IdempotentRequestInterceptor implements HandlerInterceptor {

    private static final String IDEMPOTENCY_KEY = "Idempotency-Key";
    private static final Set<String> METHODS = Set.of("POST", "PUT");

    private static final Logger logger = LoggerFactory.getLogger(IdempotentRequestInterceptor.class);

    private final RedissonClient redissonClient;

    public IdempotentRequestInterceptor(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if (!METHODS.contains(method)) {
            return true;
        }

        String key = request.getHeader(IDEMPOTENCY_KEY);
        if (key == null) {
            return true;
        }

        RBucket<String> bucket = redissonClient.getBucket(key);
        boolean success = bucket.setIfAbsent(key, Duration.of(3, ChronoUnit.SECONDS));
        if (!success) {
            logger.error("duplicated request. key : {}", key);
            throw new DuplicateRequestException("duplicated request");
        }
        return true;
    }
}
