package co.icesi.edu.structures;


public class Node<T> {
    T item;
    int priority; // para campo de prioridad
    Node<T> next;

    public Node(T item) {
        this.item = item;
        this.priority = 0; // Por defecto, la prioridad se establece en 0
        this.next = null;
    }
}
