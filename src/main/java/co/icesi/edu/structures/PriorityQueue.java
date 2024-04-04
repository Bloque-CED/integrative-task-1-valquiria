package co.icesi.edu.structures;


public class PriorityQueue<T> {
    private Node<T> front;

    public PriorityQueue() {
        this.front = null;
    }

    public void enqueue(T item, int priority) {
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
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("PriorityQueue is empty");
        }
        T item = front.item;
        front = front.next;
        return item;
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("PriorityQueue is empty");
        }
        return front.item;
    }

    public boolean isEmpty() {
        return front == null;
    }
}
