package com.example.ratelimiter.limiter;

public interface RateLimiter {
    boolean tryAcquire(String clientId);
}
