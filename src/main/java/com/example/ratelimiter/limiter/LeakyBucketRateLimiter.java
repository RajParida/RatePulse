package com.example.ratelimiter.limiter;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LeakyBucketRateLimiter implements RateLimiter {

    private final int capacity;
    private final int leakRatePerSecond;
    private final Map<String, LeakyBucket> buckets = new ConcurrentHashMap<>();

    public LeakyBucketRateLimiter(int capacity, int leakRatePerSecond) {
        this.capacity = capacity;
        this.leakRatePerSecond = leakRatePerSecond;
    }

    @Override
    public boolean tryAcquire(String clientId) {
        LeakyBucket bucket = buckets.computeIfAbsent(clientId, id -> new LeakyBucket(0, Instant.now().getEpochSecond()));
        return bucket.tryAdd();
    }

    private class LeakyBucket {
        private double stored;
        private long lastLeak;

        LeakyBucket(double stored, long lastLeak) {
            this.stored = stored;
            this.lastLeak = lastLeak;
        }

        synchronized boolean tryAdd() {
            long now = Instant.now().getEpochSecond();
            long elapsed = now - lastLeak;
            if (elapsed > 0) {
                stored = Math.max(0, stored - elapsed * leakRatePerSecond);
                lastLeak = now;
            }
            if (stored < capacity) {
                stored += 1;
                return true;
            }
            return false;
        }
    }
}
