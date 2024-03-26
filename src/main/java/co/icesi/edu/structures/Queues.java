package co.icesi.edu.structures;

public class Queues<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public Queues() {
        first = null;
        last = null;
        size = 0;
    }

    // Añade un elemento al final de la cola
    public void enqueue(T element) {
        Node<T> newElement = new Node<>(element);

        if (isEmpty()) {
            first = newElement;
        } else {
            last.setNext(newElement);
        }
        last = newElement;
        size++;
    }

    //Elimina siempre el primer elemento y a la vez retorna ese elemento que se elimina
    //Creo que se necesita una exception o se puede manejar en otro lugar, el hecho de que la cola este vacia
    public T dequeue() {
        T current = first.getElement();
        first = first.getNext();
        size--;

        return current;
    }

    //Devuelve el primer elemento de la cola sin eliminarlo
    //Tambien deberia lanzar la excepcion del metodo anterior
    public T peek() {
        return first.getElement();
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

}
