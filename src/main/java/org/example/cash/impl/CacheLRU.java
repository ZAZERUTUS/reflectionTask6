package org.example.cash.impl;

import org.example.cash.Cacheable;

import java.util.LinkedHashMap;
import java.util.Map;

public class CacheLRU<K, V> implements Cacheable {

    private final int capacity;
    private final Map<K, V> cache;

    public CacheLRU(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true);
    }

    @Override
    public Object get(Object key) {
        return  cache.getOrDefault(key, null);
    }

    @Override
    public void put(Object key, Object value) {
        if (capacity <=0) {
            return;
        }

        if (cache.size() >= capacity) {
            evictLRU();
        }

        this.cache.put((K) key, (V) value);
    }

    @Override
    public void remove(Object key) {
        cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public int size() {
        return cache.size();
    }

    private void evictLRU() {
        K eldestKey = cache.keySet().iterator().next();
        cache.remove(eldestKey);
    }
}
