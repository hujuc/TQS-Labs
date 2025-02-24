package tqs;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> {
    private LinkedList<T> stack = new LinkedList<T>();
    private int size_bound = -1;
    
    public TqsStack() {
    }

    public TqsStack(int size_bound) {
        this.size_bound = size_bound;
    }

    public T pop() {
        return stack.pop();
    }

    public int size() {
        return stack.size();
    }

    public T peek() {
        try {
            return stack.getFirst();
        } catch (Exception e) {
            throw new NoSuchElementException("Stack is empty");
        }
    }

    public void push(T element) {
        if (size_bound != -1 && stack.size() == size_bound) {
            throw new IllegalStateException("Stack is full");
        }
        stack.push(element);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public T popTopN(int n) {
        T top = null;
        for (int i = 0; i < n; i++) {
            top = stack.removeFirst();
        }
        return top;
    }
}
