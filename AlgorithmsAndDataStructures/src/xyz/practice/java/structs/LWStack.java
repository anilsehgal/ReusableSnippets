package xyz.practice.java.structs;

import xyz.practice.java.exception.LWListException;
import xyz.practice.java.exception.LWStackException;

public class LWStack<E> extends LWLinkedList<E> {

	public void push(E e) {
		addFirst(e);
	}
	
	public E pop() throws LWStackException {
		
		if (isEmpty()) {
			throw new LWStackException("stack is empty");
		}
		try {
			E e = get(0);
			removeFirst();
			return e;
		} catch (LWListException e) {
			throw new LWStackException(e.getMessage());
		}
	}
	
	public boolean isEmpty() {
		return getSize() == 0;
	}
	
	public E peek() throws LWStackException {
		if (isEmpty()) {
			throw new LWStackException("stack is empty");
		}
		E e;
		try {
			e = get(0);
			return e;
		} catch (LWListException e1) {
			throw new LWStackException(e1.getMessage());
		}
	}
}
