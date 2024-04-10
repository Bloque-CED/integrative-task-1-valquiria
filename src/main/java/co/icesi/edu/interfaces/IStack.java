package co.icesi.edu.interfaces;

public interface IStack<T> {

    public void push(T item);
    public T pop();
    public T peek();
    public boolean isEmpty();

}
