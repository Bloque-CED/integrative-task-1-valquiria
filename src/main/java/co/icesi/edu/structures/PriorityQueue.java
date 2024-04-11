package co.icesi.edu.structures;

import co.icesi.edu.interfaces.IPriorityQueue;

public class PriorityQueue<T> implements IPriorityQueue<T>{
    private Node<T> front;
    private int count; // Contador para el tamaño de la cola

    /**
     * <b>PriorityQueue Constructor</b>
     * Initializes a new empty priority queue.
     * <b>pre:</b> None.
     * <b>post:</b> A new priority queue has been created with no elements.
     */
    public PriorityQueue() {
        this.front = null;
        this.count = 0; // Inicializa el contador a 0
    }

    /**
     * <b>enqueue</b>
     * Adds an item with the specified priority to the priority queue.
     * <b>pre:</b> None.
     * <b>post:</b> The item is added to the priority queue based on its priority.
     * @param item The item to be added.
     * @param priority of the item to be added.
     */
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

    /**
     * <b>dequeue</b>
     * Removes and returns the item with the highest priority from the priority queue.
     * <b>pre:</b> The priority queue is not empty.
     * <b>post:</b> The item with the highest priority is removed and returned.
     * @return The item with the highest priority.
     */
    public T dequeue() { //eliminar
        if (isEmpty()) {
            throw new IllegalStateException("PriorityQueue is empty");
        }
        T item = front.item;
        front = front.next;
        count--; // Disminuye el contador al eliminar un elemento
        return item;
    }

    /**
     * <b>peek</b>
     * Returns the item with the highest priority from the priority queue without removing it.
     * <b>pre:</b> The priority queue is not empty.
     * <b>post:</b> The item with the highest priority is returned.
     * @return The item with the highest priority.
     */
    public T peek() { // ver
        if (isEmpty()) {
            throw new IllegalStateException("PriorityQueue is empty");
        }
        return front.item;
    }

    /**
     * <b>increasePriority</b>
     * Increases the priority of all items in the priority queue.
     * <b>pre:</b> None.
     * <b>post:</b> The priority of all items in the priority queue is increased.
     */
    public void increasePriority() {
        Node<T> current = front;
        while (current != null) {
            current.priority++;
            current = current.next;

        }
    }

    /**
     * <b>prioritizeLowest</b>
     * Prioritizes the item with the lowest priority to have the highest priority.
     * <b>pre:</b> None.
     * <b>post:</b> The item with the lowest priority is prioritized to have the highest priority.
     */
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

        // Incrementar la prioridad del nodo con la menor prioridad para hacerla la más alta. ( int highestPriority)
        lowest.priority  = (front.priority > lowest.priority) ? front.priority + 1 : lowest.priority + 1;

        // Reinsertar el nodo con la nueva prioridad. (se omitio un if != null)
        previousLowest.next = lowest.next;// Remover el nodo más bajo de su posición actual.
        // Poner el nodo más bajo (ahora con la mayor prioridad) al frente.
        lowest.next = front;
        front = lowest;

        Node<T> current0 = front;
        while (current0 != null) {
            current0.priority--;
            current0 = current0.next;

        }

    }

    /**
     * <b>isEmpty</b>
     * Checks if the priority queue is empty.
     * <b>pre:</b> None.
     * <b>post:</b> True is returned if the priority queue is empty, false otherwise.
     * @return True if the priority queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        return front == null;
    }

    /**
     * <b>size</b>
     * Returns the number of items in the priority queue.
     * <b>pre:</b> None.
     * <b>post:</b> The number of items in the priority queue is returned.
     * @return The number of items in the priority queue.
     */
    public int size() {
        return count; // Devuelve el número de elementos en la cola
    }

}
