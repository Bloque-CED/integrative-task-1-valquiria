package co.icesi.edu.structures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriorityQueue<E> {
    private List<E> heap;
    private Comparator<E> comparator;

    public PriorityQueue() {
        this.heap = new ArrayList<>();
        this.comparator = null;
    }

    public PriorityQueue(Comparator<E> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    public void enqueue(E element) {
        heap.add(element);
        int currentIndex = heap.size() - 1;
        if (comparator == null) {
            while (currentIndex > 0 && ((Comparable<E>) heap.get(currentIndex)).compareTo(heap.get(parentIndex(currentIndex))) < 0) {
                swap(currentIndex, parentIndex(currentIndex));
                currentIndex = parentIndex(currentIndex);
            }
        } else {
            while (currentIndex > 0 && comparator.compare(heap.get(currentIndex), heap.get(parentIndex(currentIndex))) < 0) {
                swap(currentIndex, parentIndex(currentIndex));
                currentIndex = parentIndex(currentIndex);
            }
        }
    }

    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }
        E root = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        heapifyDown(0);
        return root;
    }

    public E peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }
        return heap.get(0);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    private int parentIndex(int index) {
        return (index - 1) / 2;
    }

    private int leftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int rightChildIndex(int index) {
        return 2 * index + 2;
    }

    private void swap(int i, int j) {
        E temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    private void heapifyDown(int index) {
        int smallest = index;
        int leftChildIndex = leftChildIndex(index);
        int rightChildIndex = rightChildIndex(index);

        if (leftChildIndex < heap.size() && (comparator == null ? ((Comparable<E>) heap.get(leftChildIndex)).compareTo(heap.get(smallest)) < 0 :
                comparator.compare(heap.get(leftChildIndex), heap.get(smallest)) < 0)) {
            smallest = leftChildIndex;
        }

        if (rightChildIndex < heap.size() && (comparator == null ? ((Comparable<E>) heap.get(rightChildIndex)).compareTo(heap.get(smallest)) < 0 :
                comparator.compare(heap.get(rightChildIndex), heap.get(smallest)) < 0)) {
            smallest = rightChildIndex;
        }

        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }
}


/*package co.icesi.edu.structures;

import java.util.List;

public class PriorityQueues {
    private Queues<String> players;

    public PriorityQueues(List<String> namePlayers) {
        for (String namePlayer : namePlayers) {
            players.enqueue(namePlayer);
        }
    }

    //Para las veces que se salta algun jugador
    public void advanceInTheGame() {
        String firstPlayer = players.dequeue();
        players.enqueue(firstPlayer);
    }

    //Para mostrar el jugador al que le toca el turno, siempre ira de primero
    public String nextPlayer() {
        return players.peek();
    }

    //Para el inicio del juego, muestra el orden que se llevara (Hay que probarlo)
    public String showGameOrder() {
        String msg = "This is the order of the game: \n";
        Queues<String> listPlayers = players;
        int n = 0;

        while (!listPlayers.isEmpty()) {
            n++;
            msg += n + ". " + players.dequeue() + "\n";
        }
        return msg;
    }
}

 */