package org.example.cash.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CacheLFUTest {


    @Test
    public void getExistingKey_shouldReturnCachedValue() {
        //Given
        CacheLFU<String, String> cache = new CacheLFU<>(2);
        cache.put("key1", "value1");
        cache.put("key2", "value2");

        //When
        String actual = cache.get("key1");

        //Then
        assertEquals("value1", actual);
    }

    @Test
    public void getOldKey_shouldReturnNull() {
        //Given
        CacheLFU<String, String> cache = new CacheLFU<>(2);
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");

        //When
        String actual = cache.get("key1");

        //Then
        assertNull(actual);
    }

    @Test
    public void putNewItem_shouldAddToCache() {
        //Given
        CacheLFU<String, String> cache = new CacheLFU<>(2);
        cache.put("key1", "value1");

        //When
        int actual = cache.size();

        //Then
        assertEquals(1, actual);
    }

    @Test
    public void putNewItemOverCapacity_shouldEvictLFUItem() {
        //Given
        int expectedCap = 2;
        CacheLFU<String, String> cache = new CacheLFU<>(expectedCap);
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");

        //When
        int actual = cache.size();

        //Then
        assertEquals(expectedCap, actual);
    }

    @Test
    public void remove_shouldReduceCache() {
        //Given
        int expectedCap = 1;
        CacheLFU<String, String> cache = new CacheLFU<>(2);
        cache.put("key1", "value1");
        cache.put("key2", "value2");

        //When
        cache.remove("key1");
        int actual = cache.size();

        //Then
        assertEquals(expectedCap, actual);
    }

    @Test
    public void remove_shouldRemoveFromCache() {
        //Given
        String expectedCap = null;
        CacheLFU<String, String> cache = new CacheLFU<>(2);
        cache.put("key1", "value1");
        cache.put("key2", "value2");

        //When
        cache.remove("key1");
        String actual = cache.get("key1");

        //Then
        assertEquals(expectedCap, actual);
    }

    @Test
    public void clear_shouldClearCache() {
        //Given
        CacheLFU<String, String> cache = new CacheLFU<>(2);
        cache.put("key1", "value1");
        cache.put("key2", "value2");

        //When
        cache.clear();
        int actual = cache.size();

        //Then
        assertEquals(0, actual);
    }

    @Test
    public void size_shouldReturnCurrentCacheSize() {
        //Given
        CacheLFU<String, String> cache = new CacheLFU<>(2);

        //When
        cache.put("key1", "value1");
        cache.put("key2", "value2");

        //Then
        assertEquals(2, cache.size());
    }

    @Test
    public void size_shouldReturnCurrentCacheSizeIfAddMoreCapacity() {
        //Given
        CacheLFU<String, String> cache = new CacheLFU<>(2);

        //When
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");

        //Then
        assertEquals(2, cache.size());
    }

    @Test
    public void evictLFU_shouldRemoveLeastFrequentlyUsedItem() {
        //Given
        CacheLFU<String, String> cache = new CacheLFU<>(2);
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.get("key1");
        cache.put("key3", "value3");

        //When
        String actual = cache.get("key2");

        //Then
        assertNull(actual);
    }
}