package co.icesi.edu.structures;

public class Node<T> {
    T item;
    Node<T> next;

    public Node(T item) {
        this.item = item;
        this.next = null;
    }
}
