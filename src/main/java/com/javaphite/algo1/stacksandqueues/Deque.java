package com.javaphite.algo1.stacksandqueues;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * Standard deque (bidirectional queue) with head-to-tail iterator support.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> head;

    private Node<Item> tail;

    private int size;

    /**
     * Indicates if deque contains no elements.
     */
    public boolean isEmpty() {
        return 0 == size;
    }

    /**
     * Returns current number of elements in deque.
     */
    public int size() {
        return size;
    }

    /**
     * Adds one element to the head of deque.
     * New element becomes head of deque.
     */
    public void addFirst(Item item) {
        if (null == item) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            Node<Item> element = new Node<>(tail, null, item);
            head = element;
            tail = element;
        } else {
            Node<Item> element = new Node<>(head, null, item);
            head.prev = element;
            head = element;
        }
        size++;
    }

    /**
     * Adds one element to the tail of deque.
     * New element becomes tail of deque.
     */
    public void addLast(Item item) {
        if (null == item) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            Node<Item> element = new Node<>(null, head, item);
            head = element;
            tail = element;
        } else {
            Node<Item> element = new Node<>(null, tail, item);
            tail.next = element;
            tail = element;
        }
        size++;
    }

    /**
     * Removes head element of deque and returns it.
     */
    public Item removeFirst() {
        Item firstElementValue;

        switch (size) {
            case 0:
                throw new NoSuchElementException();
            case 1:
                firstElementValue = head.value;
                head = head.next;
                break;
            default:
                firstElementValue = head.value;
                head = head.next;
                head.prev = null;
        }
        size--;
        return firstElementValue;
    }

    /**
     * Removes head element of deque and returns it.
     */
    public Item removeLast() {
        Item lastElementValue;

        switch (size) {
            case 0:
                throw new NoSuchElementException();
            case 1:
                lastElementValue = tail.value;
                tail = head.prev;
                break;
            default:
                lastElementValue = tail.value;
                tail = tail.prev;
                tail.next = null;
        }
        size--;
        return lastElementValue;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator<>(this.head);
    }

    /**
     * Testing client for Deque class. No special arguments required.
     *
     * @param args ignored.
     */
    public static void main(String[] args) {
    }

    private static class DequeIterator<Item> implements Iterator<Item> {

        private Node<Item> currentElement;

        DequeIterator(Node<Item> head) {
            currentElement = head;
        }

        @Override
        public boolean hasNext() {
            return (null != currentElement);
        }

        @Override
        public Item next() {
            if (null == currentElement) {
                throw new NoSuchElementException();
            }

            Item item = currentElement.value;
            currentElement = currentElement.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super Item> action) {
            while (hasNext()) {
                action.accept(next());
            }
        }
    }

    private static class Node<Item> {

        private Node<Item> next;

        private Node<Item> prev;

        private Item value;

        Node(Node<Item> next, Node<Item> prev, Item value) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }
    }
}
