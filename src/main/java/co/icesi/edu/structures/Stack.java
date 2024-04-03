package co.icesi.edu.structures;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;

public class Stack<T> implements Iterable<T> {
    private Node<T> top;
    private int size;

    public Stack() {
        top = null;
        size = 0;
    }

    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public T pop() {
        if (top == null) throw new EmptyStackException();
        T item = top.item;
        top = top.next;
        size--;
        return item;
    }

    public T peek() {
        if (top == null) throw new EmptyStackException();
        return top.item;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    public void clear() {
        top = null;
        size = 0;
    }

    public List<T> toList() {
        List<T> list = new ArrayList<>();
        Node<T> current = top;
        while (current != null) {
            list.add(current.item);
            current = current.next;
        }
        return list;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = top;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                T item = current.item;
                current = current.next;
                return item;
            }
        };
    }

    private static class Node<T> {
        T item;
        Node<T> next;

        public Node(T item) {
            this.item = item;
            this.next = null;
        }
    }
}
