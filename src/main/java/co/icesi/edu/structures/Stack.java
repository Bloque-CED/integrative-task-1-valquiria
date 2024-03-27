package co.icesi.edu.structures;

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