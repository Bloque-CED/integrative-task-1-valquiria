package co.icesi.edu.structures;
import java.util.HashSet;
import java.util.Set;

public class HashTable<K, V> {
    private static class HashNode<K, V> {
        K key;
        V value;
        HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private HashNode<K, V>[] chainArray;
    private int capacity;
    private int size;

    public HashTable() {
        capacity = 16; // Tamaño inicial predeterminado
        chainArray = new HashNode[capacity];
        size = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    public void put(K key, V value) {
        int index = hash(key);
        HashNode<K, V> newNode = new HashNode<>(key, value);
        if (chainArray[index] == null) {
            chainArray[index] = newNode;
        } else {
            HashNode<K, V> temp = chainArray[index];
            while (temp.next != null) {
                if (temp.key.equals(key)) {
                    temp.value = value; // Actualizar el valor si la clave ya existe
                    return;
                }
                temp = temp.next;
            }
            if (temp.key.equals(key)) {
                temp.value = value;
            } else {
                temp.next = newNode;
            }
        }
        size++;
        if ((1.0*size)/capacity >= 0.7) {
            resize();
        }
    }

    public V get(K key) {
        int index = hash(key);
        HashNode<K, V> head = chainArray[index];
        while (head != null) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }
        return null; // No se encontró la clave
    }

    public boolean remove(K key) {
        int index = hash(key);
        HashNode<K, V> head = chainArray[index];
        HashNode<K, V> prev = null;
        while (head != null) {
            if (head.key.equals(key)) {
                break;
            }
            prev = head;
            head = head.next;
        }
        if (head == null) {
            return false; // No se encontró la clave
        }
        size--;
        if (prev != null) {
            prev.next = head.next;
        } else {
            chainArray[index] = head.next;
        }
        return true;
    }

    public boolean containsKey(K key) {
        int index = hash(key);
        HashNode<K, V> head = chainArray[index];
        while (head != null) {
            if (head.key.equals(key)) {
                return true;
            }
            head = head.next;
        }
        return false;
    }

    private void resize() {
        capacity *= 2;
        HashNode<K, V>[] temp = chainArray;
        chainArray = new HashNode[capacity];
        size = 0;
        for (HashNode<K, V> headNode : temp) {
            while (headNode != null) {
                put(headNode.key, headNode.value);
                headNode = headNode.next;
            }
        }
    }

    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (HashNode<K, V> headNode : chainArray) {
            while (headNode != null) {
                keys.add(headNode.key);
                headNode = headNode.next;
            }
        }
        return keys;
    }
}


/*
package co.icesi.edu.structures;

//------------------------------------------------------------------------------------------------//

import java.util.HashMap;
import java.util.Map;

//------------------------------------------------------------------------------------------------//

//Esta clase es como para almacenar la info adicional sobre las cartas, como su valor y su tipo
public class HashTable<K, V> {

    //------------------------------------------------------------------------------------------------//
    private Map<K, V> map;

    //------------------------------------------------------------------------------------------------//

    //Constructor
    public HashTable() {
        this.map = new HashMap<>();
    }

    //------------------------------------------------------------------------------------------------//

    //añadir una entrada a la tabla hash
    public void put(K key, V value) {
        map.put(key, value);
    }

    //obtener el valor asociado a una clave en la tabla hash
    public V get(K key) {
        return map.get(key);
    }

    //para verificar si la tabla hash contiene una clave específica
    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    //para eliminar una entrada de la tabla hash
    public void remove(K key) {
        map.remove(key);
    }

    //para verificar si la tabla hash está vacía
    public boolean isEmpty() {
        return map.isEmpty();
    }

    //para obtener el tamaño de la tabla hash (cantidad de entradas pues)
    public int size() {
        return map.size();
    }

    //------------------------------------------------------------------------------------------------//

}

//------------------------------------------------------------------------------------------------//

 */
