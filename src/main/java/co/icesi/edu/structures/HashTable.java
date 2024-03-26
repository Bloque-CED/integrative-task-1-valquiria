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
