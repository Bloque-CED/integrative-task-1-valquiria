package co.icesi.edu.structures;

public class HashNode<K, V> {
    K key;
    V value;
    HashNode<K, V> next; // Enlace al siguiente nodo en caso de colisión.


    /**
     * <b>HashNode</b>
     * Initializes a new hash node with the specified key and value.
     * <b>pre:</b> None.
     * <b>post:</b> A new hash node has been created with the specified key and value.
     * @param key the key of the hash node
     * @param value the value associated with the key
     */
    public HashNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}
