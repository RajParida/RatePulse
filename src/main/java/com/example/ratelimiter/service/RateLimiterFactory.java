package com.example.ratelimiter.service;

import org.springframework.stereotype.Service;

import com.example.ratelimiter.config.RateLimiterConfig;
import com.example.ratelimiter.limiter.LeakyBucketRateLimiter;
import com.example.ratelimiter.limiter.RateLimiter;
import com.example.ratelimiter.limiter.SlidingWindowRateLimiter;
import com.example.ratelimiter.limiter.TokenBucketRateLimiter;

@Service
public class RateLimiterFactory {

    private final RateLimiterConfig config;
    private final RateLimiter defaultLimiter;

    public RateLimiterFactory(RateLimiterConfig config) {
        this.config = config;
        this.defaultLimiter = createLimiter(config.getAlgorithm());
    }

    public RateLimiter getLimiter() {
        return defaultLimiter;
    }

    private RateLimiter createLimiter(String algorithm) {
        switch (algorithm) {
            case "leakyBucket":
                return new LeakyBucketRateLimiter(config.getLeakyBucket().getCapacity(), config.getLeakyBucket().getLeakRatePerSecond());
            case "slidingWindow":
                return new SlidingWindowRateLimiter(config.getSlidingWindow().getWindowSeconds(), config.getSlidingWindow().getMaxRequests());
            default:
                return new TokenBucketRateLimiter(config.getTokenBucket().getCapacity(), config.getTokenBucket().getRefillTokens(), config.getTokenBucket().getRefillPeriodSeconds());
        }
    }
}
