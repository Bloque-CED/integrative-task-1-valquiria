package co.icesi.edu.interfaces;

public interface IQueue<T> {

    public void enqueue(T item);
    public T dequeue();
    public boolean isEmpty();
}
