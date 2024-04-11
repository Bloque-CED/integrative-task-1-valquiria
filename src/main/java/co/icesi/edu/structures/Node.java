package co.icesi.edu.structures;


public class Node<T> {
    T item;
    int priority; // para campo de prioridad
    Node<T> next;

    /**
     * <b>Node</b>
     * Initializes a new node with the specified item and default priority of 0.
     * <b>pre:</b> None.
     * <b>post:</b> A new node has been created with the specified item and default priority.
     * @param item the item to be stored in the node
     */
    public Node(T item) {
        this.item = item;
        this.priority = 0; // Por defecto, la prioridad se establece en 0
        this.next = null;
    }
}
