package com.example.ratelimiter.limiter;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketRateLimiter implements RateLimiter {

    private final int capacity;
    private final int refillTokens;
    private final int refillPeriodSeconds;
    private final Map<String, TokenBucket> buckets = new ConcurrentHashMap<>();

    public TokenBucketRateLimiter(int capacity, int refillTokens, int refillPeriodSeconds) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillPeriodSeconds = refillPeriodSeconds;
    }

    @Override
    public boolean tryAcquire(String clientId) {
        TokenBucket bucket = buckets.computeIfAbsent(clientId, id -> new TokenBucket(capacity, Instant.now().getEpochSecond()));
        return bucket.tryConsume();
    }

    private class TokenBucket {
        private int tokens;
        private long lastRefill;

        TokenBucket(int tokens, long lastRefill) {
            this.tokens = tokens;
            this.lastRefill = lastRefill;
        }

        synchronized boolean tryConsume() {
            long now = Instant.now().getEpochSecond();
            long elapsed = now - lastRefill;
            if (elapsed >= refillPeriodSeconds) {
                int refillCount = (int) (elapsed / refillPeriodSeconds) * refillTokens;
                tokens = Math.min(capacity, tokens + refillCount);
                lastRefill = now;
            }
            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }
    }
}
