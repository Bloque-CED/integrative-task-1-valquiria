package co.icesi.edu.structures;

import co.icesi.edu.interfaces.IQueue;

public class Queue<T> implements IQueue<T> {
    private Node<T> front;
    private Node<T> back;

    /**
     * <b>enqueue</b>
     * Adds an item to the back of the queue.
     * <b>pre:</b> None.
     * <b>post:</b> The item is added to the back of the queue.
     */
    public Queue() {
        this.front = null;
        this.back = null;
    }

    /**
     * <b>dequeue</b>
     * Removes and returns the item at the front of the queue.
     * <b>pre:</b> The queue is not empty.
     * <b>post:</b> The item at the front of the queue is removed and returned.
     * @param item The item to be added.
     * @return The item at the front of the queue.
     */
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

    /**
     * <b>isEmpty</b>
     * Checks if the queue is empty.
     * <b>pre:</b> None.
     * <b>post:</b> True is returned if the queue is empty, false otherwise.
     * @return True if the queue is empty, false otherwise.
     */
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

    /**
     * <b>isEmpty</b>
     * Checks if the queue is empty.
     * <b>pre:</b> None.
     * <b>post:</b> True is returned if the queue is empty, false otherwise.
     * @return True if the queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        return front == null;
    }
}
