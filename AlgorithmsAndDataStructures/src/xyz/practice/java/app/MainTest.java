package xyz.practice.java.app;

import xyz.practice.java.exception.LWListException;
import xyz.practice.java.exception.LWQueueException;
import xyz.practice.java.exception.LWStackException;
import xyz.practice.java.exception.LWTreeException;
import xyz.practice.java.structs.LWBinarySearchTree;
import xyz.practice.java.structs.LWBinarySearchValueTree;
import xyz.practice.java.structs.LWHashMap;
import xyz.practice.java.structs.LWHashSet;
import xyz.practice.java.structs.LWLinkedList;
import xyz.practice.java.structs.LWQueue;
import xyz.practice.java.structs.LWStack;

public class MainTest {

	public static void main(String[] args) {
		//stackTest();
		//linkedListTest();
		//queueTest();
		//bsvtTest();
		//bstTest();
		mapTest();
	}
	
	public static void mapTest() {
		LWHashMap<Integer, String> map = new LWHashMap<>();
		map.put(5, "five");
		map.put(6, "six");
		map.put(1, "one");
		map.put(8, "eight");
		map.put(27, "twenty seven");
		map.put(7, "seven");
		map.put(18, "eighteen");
		map.put(11, "eleven");
		map.put(null, "null");
		map.put(27, "twenty seven");
		System.out.println(map.getRawData());
		map.put(18, "new eighteen");
		System.out.println(map.getRawData());
		System.out.println(map.get(6));
		System.out.println(map.get(16));
		System.out.println(map.get(11));
	}
	
	public static void setTest() {
		LWHashSet<Integer> set = new LWHashSet<>();
		set.add(5);
		set.add(6);
		set.add(1);
		set.add(8);
		set.add(27);
		set.add(7);
		set.add(18);
		set.add(11);
		set.add(27);
		set.add(18);
		System.out.println(set);
		System.out.println(set.getRawData());
	}
	
	public static void bstTest() {
		try {
			LWBinarySearchTree<Integer, String> bst = new LWBinarySearchTree<>();
			bst.put(5, "Five");
			bst.put(12, "Twelve");
			bst.put(2, "Two");
			bst.put(-4, "Minus Four");
			bst.put(3, "Three");
			bst.put(9, "Nine");
			bst.put(22, "Twenty Two");
			bst.put(25, "Twenty Five");
			bst.put(19, "Nineteen");
			bst.put(20, "Twenty");
			bst.put(21, "Twenty One");
			bst.printInorder();
			System.out.println();
			System.out.println("is 7 there?: " + bst.containsKey(7) + " value: " + bst.get(7));
			System.out.println("is 9 there?: " + bst.containsKey(9) + " value: " + bst.get(9));
			System.out.println("is -4 there?: " + bst.containsKey(-4) + " value: " + bst.get(-4));
			bst.delete(12);
			System.out.println("Remove 12");
			bst.printInorder();
			System.out.println();
			bst.delete(-4);
			System.out.println("Remove -4");
			bst.printInorder();
			System.out.println();
			System.out.println();
			System.out.println("Inorder:");
			bst.printInorder();
			System.out.println();
			System.out.println("Preorder:");
			bst.printPreorder();
			System.out.println();
			System.out.println("Postorder:");
			bst.printPostorder();
			System.out.println();
			bst.delete(0);
		} catch (LWTreeException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void bsvtTest() {
		try {
			LWBinarySearchValueTree<Integer> bsvt = new LWBinarySearchValueTree<>();
			bsvt.insert(5);
			bsvt.insert(7);
			bsvt.insert(6);
			bsvt.insert(4);
			bsvt.insert(10);
			bsvt.insert(1);
			bsvt.print();
			System.out.println("is 7 there?: " + bsvt.contains(7));
			System.out.println("is 9 there?: " + bsvt.contains(9));
			System.out.println("is 4 there?: " + bsvt.contains(4));
		} catch (LWTreeException e) {
			e.printStackTrace();
		}
	}
	
	public static void queueTest() {
		try {
			LWQueue<String> queue = new LWQueue<>();
			queue.enqueue("First");
			queue.enqueue("Second");
			queue.enqueue("Third");
			System.out.println(queue);
			System.out.println(queue.dequeue());
			System.out.println(queue);
			System.out.println();
			System.out.println(queue.front());
			System.out.println(queue.rear());
			System.out.println(queue.getSize());
			System.out.println(queue.getCapacity());
			System.out.println();
			System.out.println(queue.dequeue());
			System.out.println(queue);
			queue.enqueue("Fourth");
			System.out.println(queue);
			System.out.println(queue.front());
			System.out.println(queue.rear());
			System.out.println(queue.getSize());
			System.out.println(queue.getCapacity());
			System.out.println();
			System.out.println(queue.dequeue());
			System.out.println(queue);
			System.out.println(queue.dequeue());
			System.out.println(queue);
		} catch (LWQueueException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void stackTest() {
		try {
			LWStack<String> stack = new LWStack<>();
			stack.push("First");
			stack.push("Second");
			stack.push("Third");
			System.out.println(stack);
			System.out.println(stack.pop());
			System.out.println(stack.peek());
			System.out.println(stack.pop());
			System.out.println(stack.pop());
			//System.out.println(stack.peek());
		} catch (LWStackException e) {
			e.printStackTrace();
		}
	}
	
	public static void linkedListTest() {
		try {
			LWLinkedList<String> aList = new LWLinkedList<>();
			System.out.println(aList);
			aList.add("A");
			System.out.println(aList);
			aList.add("B");
			System.out.println(aList);
			aList.add("C");
			System.out.println(aList);
			aList.insert(2, "E");
			System.out.println(aList);
			aList.insert(2, "F");
			System.out.println(aList);
			aList.replace(2, "G");
			System.out.println(aList);
			aList.removeLast();
			System.out.println(aList);
			aList.addFirst("D");
			System.out.println(aList);
			System.out.println(aList.get(4));
			
			System.out.println(aList.get(0));
			aList.removeFirst();
			System.out.println(aList);
			System.out.println(aList.get(0));

		} catch (LWListException e) {
			e.printStackTrace();
		}
	}
}
