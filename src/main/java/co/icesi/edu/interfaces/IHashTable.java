package co.icesi.edu.interfaces;

public interface IHashTable<K, V> {

    public int hash(K key);
    public void put(K key, V value);
    public V get(K key);
    public void remove(K key);
    public boolean isEmpty();
    public int size();
}
