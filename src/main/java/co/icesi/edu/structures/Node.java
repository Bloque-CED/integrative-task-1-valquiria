package co.icesi.edu.structures;

public class Node<T> {
    private T data;
    private Node<T> next;

    // Constructor
    public Node(T data) {
        this.data = data;
        this.next = null;
    }

    // Getters y setters
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}
