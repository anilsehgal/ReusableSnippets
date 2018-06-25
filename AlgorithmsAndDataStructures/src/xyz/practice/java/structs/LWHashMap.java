package xyz.practice.java.structs;

public class LWHashMap<E extends Comparable<E>, V> {

	private int size = 10;
	private Object[] set;
	private int setSize;
	
	private class Node {
		public E e;
		public V v;
		public Node next;
		public Node(E e, V v) {
			this.e = e;
			this.v = v;
			next = null;
		}
		
		@Override
		public int hashCode() {
			if (e == null) {
				return -1;
			}
			return e.hashCode();
		}
	}
	
	public LWHashMap() {
		set = new Object[size];
	}
	
	private int getHashIndex(Node node) {
		return node.hashCode() % size;
	}
	
	@SuppressWarnings("unchecked")
	public boolean put(E e, V v) {
		if ( e == null ) return false;
		Node node = new Node(e, v);
		int currentIndex = getHashIndex(node);
		Node rootAtIndex = (Node) set[currentIndex];
		if (containsKey(e)) {
			return put(rootAtIndex, node);
		} 
		if(rootAtIndex == null) {
			set[currentIndex] = node;
			setSize++;
			return true;
		} else {
			return add( rootAtIndex, node );
		}
	}

	@SuppressWarnings("unchecked")
	public V get(E e) {
		if (!containsKey(e)) return null; 
		Node node = new Node(e, null);
		int index = getHashIndex(node);
		if (set[index] == null) {
			return null;
		} else {
			Node root = (Node) set[index];
			return get(root, node);
		}
	}
	
	private V get(Node current, Node nodeToFind) {
		if (current == null) {
			return null;
		}
		if (current.e.compareTo(nodeToFind.e) == 0) {
			return current.v;
		} else {
			return get(current.next, nodeToFind);
		}
	}
	
	private boolean put(Node current, Node nodeToUpdate) {
		if (current == null) {
			return false;
		}
		if (current.e.compareTo(nodeToUpdate.e) == 0) {
			current.v = nodeToUpdate.v;
			return true;
		} else {
			return put(current.next, nodeToUpdate);
		}
	}

	@SuppressWarnings("unchecked")
	public boolean containsKey(E e) {
		if ( e == null ) return false;
		Node node = new Node(e, null);
		int index = getHashIndex(node);
		if (set[index] == null) {
			return false;
		} else {
			Node root = (Node) set[index];
			return contains(root, node);
		}
	}
	
	private boolean contains(Node current, Node nodeToFind) {
		if (current == null) {
			return false;
		}
		if (current.e.compareTo(nodeToFind.e) == 0) {
			return true;
		} else {
			return contains(current.next, nodeToFind);
		}
	}

	private boolean add(Node currentNode, Node nodeToAdd) {
		if (currentNode.next == null) {
			currentNode.next = nodeToAdd;
			setSize++;
			return true;
		} else {
			return add(currentNode.next, nodeToAdd);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int index=0; index < size; index++) {
			if (set[index]!=null) {
				Node node = ( Node ) set[index];
				builder.append(node.e + " ");
				Node nextNode = null;
				while ( ( nextNode = node.next ) != null) {
					builder.append(nextNode.e + " ");
					node = node.next;
				}
			}
		}
		return builder.toString();
	}
	
	public int getSize() {
		return setSize;
	}
	
	@SuppressWarnings("unchecked")
	public String getRawData() {
		StringBuilder builder = new StringBuilder();
		for (int index=0; index < size; index++) {
			if (set[index]!=null) {
				Node node = ( Node ) set[index];
				builder.append("(" + node.e+ ", " + node.v + ")");
				Node nextNode = null;
				while ( ( nextNode = node.next ) != null) {
					builder.append(" " + "(" + nextNode.e + ", " + nextNode.v + ")");
					node = node.next;
				}
				builder.append("\n");
			} else {
				builder.append("null\n");
			}
		}
		return builder.toString();
	}
}
