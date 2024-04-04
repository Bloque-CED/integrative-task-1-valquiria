package co.icesi.edu.structures;

import java.util.HashMap;
import java.util.Map;


public class HashTable<K, V> {
    private static final int INITIAL_CAPACITY = 108;
    private static final double LOAD_FACTOR = 0.75;

    private int size;
    private int capacity;
    private Map<K, V>[] table;

    public HashTable() {
        this.size = 0;
        this.capacity = INITIAL_CAPACITY;
        this.table = new Map[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new HashMap<>();
        }
    }

    public void put(K key, V value) {
        if (size >= LOAD_FACTOR * capacity) {
            resize();
        }
        int index = getIndex(key);
        table[index].put(key, value);
        size++;
    }

    public V get(K key) {
        int index = getIndex(key);
        return table[index].get(key);
    }

    public boolean containsKey(K key) {
        int index = getIndex(key);
        return table[index].containsKey(key);
    }

    public void remove(K key) {
        int index = getIndex(key);
        table[index].remove(key);
        size--;
    }

    public int size() {
        return size;
    }

    private int getIndex(K key) {
        return Math.abs(key.hashCode() % capacity);
    }

    private void resize() {
        capacity *= 2;
        size = 0;
        Map<K, V>[] oldTable = table;
        table = new Map[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new HashMap<>();
        }
        for (Map<K, V> map : oldTable) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }
}
