package org.example.cash;

import org.example.cash.impl.CacheLFU;
import org.example.cash.impl.CacheLRU;

import static org.example.config.ConfigHandler.getInstanceConfig;

public class CacheFactory {

    public static <K, V> Cacheable<K, V> createCache() {
        switch (getInstanceConfig().getConfig().getTypeCache()) {
            case "LRU":
                System.out.println("Create - CacheLRU");
                return new CacheLRU<>(getInstanceConfig().getConfig().getCapacity());
            case "LFU":
                System.out.println("Create - CacheLFU");
                return new CacheLFU<>(getInstanceConfig().getConfig().getCapacity());
            default:
                return null;
        }
    }
}
