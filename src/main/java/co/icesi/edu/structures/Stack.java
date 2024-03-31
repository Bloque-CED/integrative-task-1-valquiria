package co.icesi.edu.structures;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Stack<E> implements Iterable<E> {
    private Node<E> top;
    private int size;

    public Stack() {
        //
    }

    public void push(E item) {
        Node<E> newNode = new Node<>(item);
        if (isEmpty()) {
            top = newNode;
        } else {
            newNode.next = top;
            top = newNode;
        }
        size++;
    }

    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        E item = top.item;
        top = top.next;
        size--;
        return item;
    }

    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public E[] toArray() {
        @SuppressWarnings("unchecked")
        E[] array = (E[]) new Object[size];
        Node<E> current = top;
        int index = 0;
        while (current != null) {
            array[index++] = current.item;
            current = current.next;
        }
        return array;
    }

    public void clear() {
        top = null;
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new StackIterator();
    }

    private class Node<T> {
        private T item;
        private Node<T> next;

        public Node(T item) {
            this.item = item;
            this.next = null;
        }
    }

    private class StackIterator implements Iterator<E> {
        private Node<E> current = top;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            E item = current.item;
            current = current.next;
            return item;
        }
    }

    // Puedes mantener este método si lo necesitas
    public List<E> toList() {
        List<E> list = new ArrayList<>();
        Node<E> current = top;
        while (current != null) {
            list.add(current.item);
            current = current.next;
        }
        return list;
    }
}



/*package co.icesi.edu.structures;

public class Stack<T> {
    private Node<T> top; // Nodo superior de la pila
    private int size; // Tamaño de la pila

    // Constructor para inicializar la pila
    public Stack() {
        top = null;
        size = 0;
    }

    // Método para agregar un elemento a la pila (push)
    public void push(T element) {
        Node<T> newNode = new Node<>(element);
        newNode.setNext(top);
        top = newNode;
        size++;
    }

    // Método para remover y devolver el elemento superior de la pila (pop)
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("La pila está vacía");
        }
        T data = top.getElement();
        top = top.getNext();
        size--;
        return data;
    }

    // Método para obtener el elemento superior de la pila sin removerlo (peek)
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("La pila está vacía");
        }
        return top.getElement();
    }

    // Método para verificar si la pila está vacía
    public boolean isEmpty() {
        return top == null;
    }

    // Método para obtener el tamaño de la pila
    public int size() {
        return size;
    }
}

 */