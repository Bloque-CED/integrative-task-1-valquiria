package co.icesi.edu.structures;

public class Queue<T> {
    private Node<T> front;
    private Node<T> back;

    public Queue() {
        this.front = null;
        this.back = null;
    }

    public void enqueue(T item) { //agregar
        Node<T> newNode = new Node<>(item);
        if (isEmpty()) {
            front = newNode;
            back = newNode;
        } else {
            back.next = newNode;
            back = newNode;
        }
    }

    public T dequeue() { //eliminar
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        T item = front.item;
        front = front.next;
        if (front == null) {
            back = null;
        }
        return item;
    }

    public T peek() { //ver
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return front.item;
    }

    public boolean isEmpty() {
        return front == null;
    }
}
