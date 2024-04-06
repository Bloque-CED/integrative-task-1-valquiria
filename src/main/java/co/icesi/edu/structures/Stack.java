package co.icesi.edu.structures;

public class Stack<T> {
    private Node<T> top;

    public Stack() {
        this.top = null;
    }

    public void push(T item) { //añadir
        Node<T> newNode = new Node<>(item);
        newNode.next = top;
        top = newNode;
    }

    public T pop() { //eliminar
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        T item = top.item;
        top = top.next;
        return item;
    }

    public T peek() { //ver
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return top.item;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
