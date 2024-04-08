package co.icesi.edu.structures;


public class HashTable<K, V> {
    private HashNode<K, V>[] table;
    private int size;
    private int capacity;

    public HashTable() {
        this.capacity = 5;
        table = new HashNode[capacity];
        this.size = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

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
            }
        }
    }

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

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}

