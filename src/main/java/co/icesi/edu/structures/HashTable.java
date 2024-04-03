package co.icesi.edu.structures;

import java.util.HashMap;
import java.util.Map;


public class HashTable<K, V> {
    private Map<K, V> map;

    public HashTable() {
        map = new HashMap<>();
    }

    public void put(K key, V value) {
        map.put(key, value);
    }

    public V get(K key) {
        return map.get(key);
    }

    public boolean remove(K key) {
        return map.remove(key) != null;
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }
}
