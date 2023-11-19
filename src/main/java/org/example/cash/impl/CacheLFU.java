package org.example.cash.impl;

import org.example.cash.Cacheable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CacheLFU<K, V> implements Cacheable {

    private static final Logger logger = LoggerFactory.getLogger(CacheLFU.class);

    private final int capacity;
    private final Map<K, V> cache;
    private final Map<K, Integer> frequencies;

    public CacheLFU(int capacity) {
        logger.info("Setup class");
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true);
        this.frequencies = new HashMap<>();
    }

    @Override
    public Object get(Object key) {
        if (cache.containsKey(key)) {
            updateFrequency((K) key);
        }
        return cache.getOrDefault(key, null);
    }

    @Override
    public void put(Object key, Object value) {
        if (capacity <= 0) {
            return;
        }

        if (cache.size() >= capacity) {
            evictLFU();
        }

        cache.put((K) key, (V) value);
        frequencies.put((K) key, 1);
    }

    @Override
    public void remove(Object key) {
        cache.clear();
        frequencies.clear();
    }

    @Override
    public void clear() {
        cache.clear();
        frequencies.clear();
    }

    @Override
    public int size() {
        return cache.size();
    }

    private void evictLFU() {
        int minFrequency = frequencies.values().stream().min(Integer::compareTo).orElse(0);
        K minKey = frequencies.entrySet().stream()
                .filter(entry -> entry.getValue() == minFrequency)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);

        if (minKey != null) {
            cache.remove(minKey);
            frequencies.remove(minKey);
        }
    }

    private void updateFrequency(K key) {
        frequencies.put(key, frequencies.getOrDefault(key, 0) + 1);
    }
}
