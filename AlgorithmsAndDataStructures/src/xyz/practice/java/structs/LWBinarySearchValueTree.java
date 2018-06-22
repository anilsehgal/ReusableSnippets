package xyz.practice.java.structs;

import xyz.practice.java.exception.LWTreeException;

public class LWBinarySearchValueTree<E extends Comparable<E>> {

	private Node root;
	private int size;
	
	private class Node {
		public E e;
		public Node left;
		public Node right;
		public Node(E e) {
			this.e = e;
		}
	}
	
	public void insert(E e) throws LWTreeException {
		if (e == null) {
			throw new LWTreeException("Argument cannot be null");
		}
		if (root == null) {
			root = new Node(e);
			size++;
		} else {
			if ( e.compareTo( root.e ) > 0 ) {
				root.right = insertRecursive(root.right, e);
			} else {
				root.left = insertRecursive(root.left, e);
			}
		}
	}

	private Node insertRecursive(Node node, E e) throws LWTreeException {
		if (e == null) {
			throw new LWTreeException("Argument cannot be null");
		}
		if (node == null) {
			node = new Node(e);
			size++;
			return node;
		} else {
			if ( e.compareTo( node.e ) > 0 ) {
				node.right = insertRecursive(node.right, e);
			} else {
				node.left = insertRecursive(node.left, e);
			}
		}
		return node;
	}
	
	public boolean contains(E e) throws LWTreeException {
		
		if (root == null) {
			throw new LWTreeException("Tree is empty");
		}
		if (e == null) {
			throw new LWTreeException("Argument cannot be null");
		}
		return searchInTree(root, e);
	}
	
	private boolean searchInTree(Node node, E e) {
		if (node == null) {
			return false;
		}
		if ( e.compareTo( node.e ) > 0 ) {
			return searchInTree(node.right, e);
		} else if( e.compareTo( node.e ) < 0 ) {
			return searchInTree(node.left, e);
		} else {
			return true;
		}
	}

	public void print() {
		if (root == null) {
			System.out.println("Tree is empty");
			return;
		}
		printNodeData(root);
	}
	
	public int size() {
		return size;
	}
	
	private void printNodeData(Node node) {
		if (node == null) {
			return;
		} else {
			System.out.println("  Value: " + node.e);
			if (node.left != null) {
				System.out.println("  Left: " + node.left.e);
				printNodeData(node.left);
			} else {
				System.out.println("  Left: " + null);
			}
			if (node.right != null) {
				System.out.println("  Right: " + node.right.e);
				printNodeData(node.right);
			} else {
				System.out.println("  Right: " + null);
			}
		}
	}
}
