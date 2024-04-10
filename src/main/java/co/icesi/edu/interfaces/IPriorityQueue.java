package co.icesi.edu.interfaces;

public interface IPriorityQueue<T> {

    public void enqueue(T item, int priority);
    public T dequeue();
    public T peek();
    public void increasePriority();
    public void prioritizeLowest();
    public boolean isEmpty();
    public int size();

}
