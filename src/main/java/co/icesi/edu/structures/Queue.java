package co.icesi.edu.structures;

import java.util.Iterator;

public class Queue<E> implements Iterable<E> {

    private Node<E> first;
    private Node<E> last;
    private int size;

    public Queue() {
        first = null;
        last = null;
        size = 0;
    }

    public void enqueue(E item) {
        Node<E> newNode = new Node<>(item);
        if (isEmpty()) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    public E dequeue() {
        if (isEmpty()) {
            return null;
        }
        E item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        size--;
        return item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return first.item;
    }

    public boolean contains(E item) {
        Node<E> current = first;
        while (current != null) {
            if (current.item.equals(item)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean remove(E item) {
        if (isEmpty()) {
            return false;
        }
        if (first.item.equals(item)) {
            first = first.next;
            size--;
            return true;
        }
        Node<E> current = first;
        while (current.next != null) {
            if (current.next.item.equals(item)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new QueueIterator();
    }

    private class Node<T> {
        private T item;
        private Node<T> next;

        public Node(T item) {
            this.item = item;
            this.next = null;
        }
    }

    private class QueueIterator implements Iterator<E> {
        private Node<E> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                return null;
            }
            E item = current.item;
            current = current.next;
            return item;
        }
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node<E> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }
}
