package xyz.practice.java.structs;

import xyz.practice.java.exception.LWListException;

public class LWLinkedList<E> {
	private LWNode first;
	private int size;
	private class LWNode {
		E e;
		LWNode next;
		public LWNode(E e) {
			this.e = e;
			next = null;
		}
		@Override
		public String toString() {
			return e.toString();
		}
	}
	
	public void insert(int index, E e) throws LWListException {
		
		if (index < 0) {
			throw new LWListException("Minimum value of index is 0");
		}
		if (index == 0) {
			addFirst(e);
		} else {
			if (index > size) {
				throw new LWListException("index " + index + " does not exist in LWLinkedList of size: " + size);
			} else {
				LWNode curr = first;
				for(int i = 0; i < index - 1; i++) {
					curr = curr.next;
				}
				LWNode nextToCurrent = curr.next;
				LWNode nodeToAdd = new LWNode(e);
				nodeToAdd.next = nextToCurrent;
				curr.next = nodeToAdd;
				size++;
			}
		}
	}
	
	public void replace(int index, E e) throws LWListException {
		
		if (index < 0) {
			throw new LWListException("Minimum value of index is 0");
		}
		if (index > size) {
			throw new LWListException("index " + index + " does not exist in LWLinkedList of size: " + size);
		} else {
			LWNode prev = first;
			for(int i = 0; i < index - 1; i++) {
				prev = prev.next;
			}
			LWNode curr = prev.next;
			LWNode nextToCurrent = curr.next;
			LWNode nodeToAdd = new LWNode(e);
			nodeToAdd.next = nextToCurrent;
			prev.next = nodeToAdd;
		}
	}
	
	public E get(int index) throws LWListException {
		
		if (index < 0) {
			throw new LWListException("Minimum value of index is 0");
		}
		if (size == 0) {
			throw new LWListException("No elements found in LWLinkedList");
		}
		if (index > size) {
			throw new LWListException("index " + index + " does not exist in LWLinkedList of size: " + size);
		} else {
			LWNode curr = first;
			for(int i = 0; i < index; i++) {
				curr = curr.next;
			}
			return curr.e;
		}
	}
	
	public void addFirst(E e) {
		if (first == null) {
			first = new LWNode(e);
		} else {
			LWNode newFirst = new LWNode(e);
			LWNode currentFirst = first;
			newFirst.next = currentFirst;
			first = newFirst;
		}
		size++;
	}
	
	public void removeFirst() throws LWListException {
		if (first == null) {
			throw new LWListException("No contents found");
		} else {
			first = first.next;
			size--;
		}
	}
	
	public void add(E e) {
		if (first == null) {
			first = new LWNode(e);
		} else {
			LWNode curr = first;
			for(int i = 0; i < size-1; i++) {
				curr = curr.next;
			}
			curr.next = new LWNode(e);
		}
		size++;
	}
	
	public void removeLast() throws LWListException {
		if (first == null) {
			throw new LWListException("No elements in list");
		} else {
			LWNode curr = first;
			for(int i = 0; i < size-1; i++) {
				curr = curr.next;
			}
			curr.next = null;
			size--;
			if (curr == first) {
				first = null;
				size = 0;
			}
		}
	}
	
	public int getSize() {
		return size;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[");
		LWNode curr = first;
		if (size != 0) {
			builder.append(curr);
			builder.append(",");
		}
		for(int i = 0; i < size-1; i++) {
			curr = curr.next;
			builder.append(curr+",");
		}
		String printThis = builder.toString();
		if (size > 0) {
			printThis = printThis.substring(0, printThis.length() - 1);
		}
		return printThis + "]";
	}
}
