package co.icesi.edu.structures;

import co.icesi.edu.interfaces.IHashTable;


public class HashTable<K, V> implements IHashTable<K, V>{

    private HashNode<K, V>[] table;
    private int size;
    private int capacity;

    /**
     * <b>HashTable</b>
     * Initializes a new hash table with a default capacity of 37.
     * <b>pre:</b> None.
     * <b>post:</b> A new hash table has been created with the default capacity and size set to 0.
     */
    public HashTable() {
        this.capacity = 37;
        table = new HashNode[capacity];
        this.size = 0;
    }

    /**
     * <b>hash</b>
     * Calculates the hash value for the specified key.
     * <b>pre:</b> None.
     * <b>post:</b> The hash value for the specified key has been calculated.
     * @param key the key to calculate the hash value for
     * @return the hash value for the specified key
     */
    public int hash(K key) {
        return (key == null) ? 0 : Math.abs(key.hashCode()) %capacity;
    }

    /**
     * <b>put</b>
     * Inserts a key-value pair into the hash table.
     * <b>pre:</b> The key and value are not null.
     * <b>post:</b> The key-value pair has been inserted into the hash table.
     * @param key the key to insert
     * @param value the value to insert
     */
    public void put(K key, V value) {
        int index = hash(key);
        HashNode<K, V> newNode = new HashNode<>(key, value);

        if (table[index] == null) {
            table[index] = newNode;
            size++;
        } else {
            HashNode<K, V> current = table[index];
            while (current.next != null) {
                if (current.key.equals(key)) {
                    current.value = value; // Actualiza el valor si la clave ya existe
                    return;
                }
                current = current.next;
            }
            if (current.key.equals(key)) {
                current.value = value;
            } else {
                current.next = newNode;
                size++;
            }
        }
    }

    /**
     * <b>get</b>
     * Retrieves the value associated with the specified key from the hash table.
     * <b>pre:</b> None.
     * <b>post:</b> The value associated with the specified key has been retrieved from the hash table.
     * @param key the key whose associated value is to be retrieved
     * @return the value associated with the specified key, or null if the key is not found
     */
    public V get(K key) {
        int index = hash(key);
        HashNode<K, V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * <b>remove</b>
     * Removes the key-value pair associated with the specified key from the hash table.
     * <b>pre:</b> None.
     * <b>post:</b> The key-value pair associated with the specified key has been removed from the hash table.
     * @param key the key whose associated key-value pair is to be removed
     */
    public void remove(K key) {
        int index = hash(key);
        HashNode<K, V> current = table[index];
        HashNode<K, V> prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev != null) {
                    prev.next = current.next;
                } else {
                    table[index] = current.next;
                }
                size--;
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    /**
     * <b>isEmpty</b>
     * Checks whether the hash table is empty.
     * <b>pre:</b> None.
     * <b>post:</b> None.
     * @return true if the hash table is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * <b>size</b>
     * Returns the number of key-value pairs in the hash table.
     * <b>pre:</b> None.
     * <b>post:</b> None.
     * @return the number of key-value pairs in the hash table
     */
    public int size() {
        return size;
    }
}

