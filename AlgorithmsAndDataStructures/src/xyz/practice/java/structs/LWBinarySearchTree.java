package xyz.practice.java.structs;

import xyz.practice.java.exception.LWTreeException;

public class LWBinarySearchTree<E extends Comparable<E>, V> {

	private Node root;
	private int size;

	private class Node {
		public E e;
		public V v;
		public Node left;
		public Node right;
		public Node(E e, V v) {
			this.e = e;
			this.v = v;
		}
	}

	public void put(E e, V v) throws LWTreeException {
		if (e == null) {
			throw new LWTreeException("Argument cannot be null");
		}
		if (root == null) {
			root = new Node(e, v);
			size++;
		} else {
			if ( e.compareTo( root.e ) > 0 ) {
				root.right = insertRecursive(root.right, e, v);
			} else {
				root.left = insertRecursive(root.left, e, v);
			}
		}
	}

	private Node insertRecursive(Node node, E e, V v) throws LWTreeException {
		if (e == null) {
			throw new LWTreeException("Key cannot be null");
		}
		if (node == null) {
			node = new Node(e, v);
			size++;
			return node;
		} else {
			if ( e.compareTo( node.e ) > 0 ) {
				node.right = insertRecursive(node.right, e, v);
			} else {
				node.left = insertRecursive(node.left, e, v);
			}
		}
		return node;
	}

	public boolean containsKey(E e) throws LWTreeException {

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

	public V get(E e) throws LWTreeException {

		if (root == null) {
			throw new LWTreeException("Tree is empty");
		}
		if (e == null) {
			throw new LWTreeException("Key cannot be null");
		}
		return get(root, e);
	}

	public boolean delete(E e) throws LWTreeException {
		if (root == null) {
			throw new LWTreeException("Tree is empty");
		}
		if (e == null) {
			throw new LWTreeException("Key cannot be null");
		}
		Node parentOfNodeToRemove = null;
		Node nodeToRemove = root;

		while( nodeToRemove != null ) {
			if ( e.compareTo(nodeToRemove.e) > 0 ) {
				parentOfNodeToRemove = nodeToRemove; 
				nodeToRemove = nodeToRemove.right;
			} else if ( e.compareTo(nodeToRemove.e) < 0 ) {
				parentOfNodeToRemove = nodeToRemove; 
				nodeToRemove = nodeToRemove.left;
			} else {
				break;
			}
		}
		if (nodeToRemove == null) {
			throw new LWTreeException("Key " + e + " not found");
		}

		if (nodeToRemove.left != null && nodeToRemove.right != null) {
			// CASE 1: two children
			// find the min in right subtree
			Node minValueNode = nodeToRemove.right;
			Node minValueNodeParent = nodeToRemove;
			while (minValueNode.left != null) {
				minValueNodeParent = minValueNode;
				minValueNode = minValueNode.left;
			}
			// if nodeToRemove is right of parent
			if ( parentOfNodeToRemove.right.e.compareTo(nodeToRemove.e) == 0 ) {
				parentOfNodeToRemove.right.e = minValueNode.e;
				parentOfNodeToRemove.right.v = minValueNode.v;
			} else if (parentOfNodeToRemove.left.e.compareTo(nodeToRemove.e) == 0) {
				parentOfNodeToRemove.left.e = minValueNode.e;
				parentOfNodeToRemove.left.v = minValueNode.v;
			}
			// since this is the min value node, the left will be null
			// what if it has a right
			if (minValueNode.right != null) {
				minValueNodeParent.left = minValueNode.right;
			}
		} else if (nodeToRemove.left == null && nodeToRemove.right == null) {
			// CASE 2: both children are null
			if (parentOfNodeToRemove.left!=null && parentOfNodeToRemove.left.e.compareTo(nodeToRemove.e) == 0) {
				parentOfNodeToRemove.left = null;
			} else if (parentOfNodeToRemove.right!=null && parentOfNodeToRemove.right.e.compareTo(nodeToRemove.e) == 0) {
				parentOfNodeToRemove.right = null;
			}
		} else {
			if ( parentOfNodeToRemove.left!= null && parentOfNodeToRemove.left.e.compareTo(nodeToRemove.e) == 0 ) {
				// CASE 3: one child: if current is left child of parent, current has left or right child
				parentOfNodeToRemove.left = nodeToRemove.left != null ? nodeToRemove.left : nodeToRemove.right;
			} else if ( parentOfNodeToRemove.right!= null && parentOfNodeToRemove.right.e.compareTo(nodeToRemove.e) == 0 ) {
				// CASE 3: one child: if current is right child of parent, current has left or right child
				parentOfNodeToRemove.right = nodeToRemove.left != null ? nodeToRemove.left : nodeToRemove.right ;
			}
		}
		size--;
		return true;
	}
	public void printInorder() {
		printInorder(root);
	}
	
	private void printInorder(Node node) {
		if (node == null)
			return;
		printInorder(node.left);
		System.out.print("("+node.e+": "+node.v+")" + " ");
		printInorder(node.right);
	}
	
	public void printPreorder() {
		printPreorder(root);
	}
	
	private void printPreorder(Node node) {
		if (node == null)
			return;
		System.out.print("("+node.e+": "+node.v+")" + " ");
		printInorder(node.left);
		printInorder(node.right);
	}
	
	public void printPostorder() {
		printPostorder(root);
	}
	
	private void printPostorder(Node node) {
		if (node == null)
			return;
		printInorder(node.left);
		printInorder(node.right);
		System.out.print("("+node.e+": "+node.v+")" + " ");
	}

	private V get(Node node, E e) {
		if (node == null) {
			return null;
		}
		if ( e.compareTo( node.e ) > 0 ) {
			return get(node.right, e);
		} else if( e.compareTo( node.e ) < 0 ) {
			return get(node.left, e);
		} else {
			return node.v;
		}
	}

	public int size() {
		return size;
	}

}
