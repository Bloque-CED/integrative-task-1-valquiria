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

    public void increasePriority() {
        Node<T> current = front;
        while (current != null) {
            current.priority++;
            current = current.next;

        }
    }


    public void prioritizeLowest() {
        if (isEmpty() || front.next == null) {
            return; // No hay suficientes elementos para reordenar.
        }

        Node<T> current = front;
        Node<T> lowest = front; // Para guardar el nodo con la menor prioridad.
        Node<T> previousLowest = null; // Para mantener el nodo anterior al más bajo encontrado.

        while (current.next != null) {
            if (current.next.priority < lowest.priority) {
                lowest = current.next;
                previousLowest = current;
            }
            current = current.next;
        }

        if (lowest == front) {
            // El elemento con la menor prioridad ya está al frente.
            return;
        }

        // Incrementar la prioridad del nodo con la menor prioridad para hacerla la más alta.
        int highestPriority = (front.priority > lowest.priority) ? front.priority + 1 : lowest.priority + 1;
        lowest.priority = highestPriority;

        // Reinsertar el nodo con la nueva prioridad.
        if (previousLowest != null) {
            previousLowest.next = lowest.next; // Remover el nodo más bajo de su posición actual.
        }

        // Poner el nodo más bajo (ahora con la mayor prioridad) al frente.
        lowest.next = front;
        front = lowest;
    }


    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return count; // Devuelve el número de elementos en la cola
    }
}
