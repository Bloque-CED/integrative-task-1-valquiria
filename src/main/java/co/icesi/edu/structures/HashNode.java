package co.icesi.edu.structures;

public class HashNode<K, V> {
    K key;
    V value;
    HashNode<K, V> next; // Enlace al siguiente nodo en caso de colisión.

    public HashNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}
