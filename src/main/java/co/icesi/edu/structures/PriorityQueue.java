package co.icesi.edu.structures;

public class PriorityQueue<T> {
    private Node<T> front;
    private int count; // Contador para el tamaño de la cola

    public PriorityQueue() {
        this.front = null;
        this.count = 0; // Inicializa el contador a 0
    }

    public void enqueue(T item, int priority) { //añadir
        Node<T> newNode = new Node<>(item);
        newNode.priority = priority;

        if (isEmpty() || priority > front.priority) {
            newNode.next = front;
            front = newNode;
        } else {
            Node<T> current = front;
            while (current.next != null && priority <= current.next.priority) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        count++; // Incrementa el contador al añadir un nuevo elemento
    }

    public T dequeue() { //eliminar
        if (isEmpty()) {
            throw new IllegalStateException("PriorityQueue is empty");
        }
        T item = front.item;
        front = front.next;
        count--; // Disminuye el contador al eliminar un elemento
        return item;
    }

    public T peek() { // ver
        if (isEmpty()) {
            throw new IllegalStateException("PriorityQueue is empty");
        }
        return front.item;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return count; // Devuelve el número de elementos en la cola
    }
}
