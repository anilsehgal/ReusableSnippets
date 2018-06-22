package xyz.practice.java.structs;

import java.util.Arrays;

import xyz.practice.java.exception.LWQueueException;

public class LWQueue<E extends Object> {
	Object[] array;
	final int max;
	int frontEnd;
	int rearEnd;
	public LWQueue() {
		max = 100;
		array = new Object[max];
	}
	
	public LWQueue(int size) throws LWQueueException {
		if (size > 0) {
			array = new Object[size];
			max = size;
		} else {
			throw new LWQueueException("Size must be greater than 0");
		}
	}
	
	public void enqueue(E e) throws LWQueueException {
		if (rearEnd == max-1) {
			throw new LWQueueException("Queue capacity reached");
		}
		array[rearEnd] = e;
		rearEnd++;
	}
	
	@SuppressWarnings("unchecked")
	public E dequeue() throws LWQueueException {
		if (rearEnd == frontEnd) {
			throw new LWQueueException("No elements in queue");
		}
		E e = (E) array[frontEnd];
		frontEnd++;
		return e;
	}
	
	@SuppressWarnings("unchecked")
	public E front() throws LWQueueException {
		if (rearEnd == frontEnd) {
			throw new LWQueueException("No elements in queue");
		}
		E e = (E) array[frontEnd];
		return e;
	}
	
	@SuppressWarnings("unchecked")
	public E rear() throws LWQueueException {
		if (rearEnd == frontEnd) {
			throw new LWQueueException("No elements in queue");
		}
		E e = (E) array[rearEnd-1];
		return e;
	}
	
	public int getSize() {
		return rearEnd - frontEnd;
	}
	
	public boolean isEmpty() {
		return rearEnd == frontEnd;
	}
	
	public int getCapacity() {
		return max;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
	
		E printer[] = (E[]) Arrays.copyOfRange(array, frontEnd, rearEnd);
		return Arrays.toString(printer);
	}
}
