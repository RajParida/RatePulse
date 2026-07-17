package com.example.ratelimiter.limiter;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowRateLimiter implements RateLimiter {

    private final int windowSeconds;
    private final int maxRequests;
    private final Map<String, Deque<Long>> windows = new ConcurrentHashMap<>();

    public SlidingWindowRateLimiter(int windowSeconds, int maxRequests) {
        this.windowSeconds = windowSeconds;
        this.maxRequests = maxRequests;
    }

    @Override
    public boolean tryAcquire(String clientId) {
        Deque<Long> queue = windows.computeIfAbsent(clientId, id -> new ArrayDeque<>());
        long now = Instant.now().getEpochSecond();
        synchronized (queue) {
            while (!queue.isEmpty() && queue.peekFirst() <= now - windowSeconds) {
                queue.pollFirst();
            }
            if (queue.size() < maxRequests) {
                queue.offerLast(now);
                return true;
            }
            return false;
        }
    }
}
