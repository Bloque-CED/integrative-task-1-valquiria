package co.icesi.edu.structures;

import co.icesi.edu.interfaces.IStack;

public class Stack<T> implements IStack<T> {
    private Node<T> top;

    /**
     * <b>Stack()</b>
     * Constructs an empty stack.
     * <b>pre:</b> None.
     * <b>post:</b> An empty stack is created with top set to null.
     */
    public Stack() {
        this.top = null;
    }


    /**
     * <b>push</b>
     * Adds an item to the top of the stack.
     * <b>pre:</b> None.
     * <b>post:</b> The item is added to the top of the stack.
     * @param item The item to be added.
     */
    public void push(T item) { //añadir
        Node<T> newNode = new Node<>(item);
        newNode.next = top;
        top = newNode;
    }

    /**
     * <b>pop</b>
     * Removes and returns the item at the top of the stack.
     * <b>pre:</b> The stack is not empty.
     * <b>post:</b> The item at the top of the stack is removed and returned.
     * @return The item at the top of the stack.
     */
    public T pop() { //eliminar
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        T item = top.item;
        top = top.next;
        return item;
    }

    /**
     * <b>peek</b>
     * Returns the item at the top of the stack without removing it.
     * <b>pre:</b> The stack is not empty.
     * <b>post:</b> The item at the top of the stack is returned.
     * @return The item at the top of the stack.
     */
    public T peek() { //ver
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return top.item;
    }

    /**
     * <b>isEmpty</b>
     * Checks if the stack is empty.
     * <b>pre:</b> None.
     * <b>post:</b> True is returned if the stack is empty, false otherwise.
     * @return True if the stack is empty, false otherwise.
     */
    public boolean isEmpty() {
        return top == null;
    }
}
