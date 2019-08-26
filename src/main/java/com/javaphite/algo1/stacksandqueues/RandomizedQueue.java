package com.javaphite.algo1.stacksandqueues;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * Queue implementation with random order of elements iteration.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int DEFAULT_INITIAL_CAPACITY = 10;

    private ResizingArray<Item> queue;

    /**
     * Creates new empty randomized queue.
     */
    public RandomizedQueue() {
        queue = new ResizingArray<>(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Indicates if queue contains no elements.
     */
    public boolean isEmpty() {
        return 0 == queue.size();
    }

    /**
     * Returns current number of elements in queue.
     */
    public int size() {
        return queue.size();
    }

    /**
     * Adds item to queue.
     */
    public void enqueue(Item item) {
        if (null == item) {
            throw new IllegalArgumentException();
        }

        queue.add(item);
    }

    /**
     * Removes and returns a random item from queue
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty!");
        }
        return queue.remove(StdRandom.uniform(size()));
    }

    /**
     * Returns a random item (but not removes it)
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty!");
        }
        return queue.get(StdRandom.uniform(size()));
    }

    /**
     * Returns an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>(queue);
    }

    public static void main(String[] args) {
        RandomizedQueue<Character> queue = new RandomizedQueue<>();
        if (queue.isEmpty()) {
            queue.enqueue('A');
            queue.enqueue('B');
            StdOut.println(queue.dequeue());
            queue.forEach(StdOut::println);
            StdOut.println(queue.size());
            StdOut.println(queue.sample());
            Iterator<Character> iterator = queue.iterator();
            iterator.forEachRemaining(StdOut::println);
        }
    }

    private static class RandomizedQueueIterator<Item> implements Iterator<Item> {

        private ResizingArray<Item> iterationOrder;

        RandomizedQueueIterator(ResizingArray<Item> items) {
            iterationOrder = new ResizingArray<>(items.size());
            copyItemsInRandomOrder(items, iterationOrder);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported by RandomizedQueueIterator");
        }

        @Override
        public void forEachRemaining(Consumer<? super Item> action) {
            while (hasNext()) {
                action.accept(next());
            }
        }

        @Override
        public boolean hasNext() {
            return 0 < iterationOrder.size();
        }

        @Override
        public Item next() {
            if (0 == iterationOrder.size()) {
                throw new NoSuchElementException();
            }
            return iterationOrder.remove(0);
        }

        private void copyItemsInRandomOrder(final ResizingArray<Item> source, ResizingArray<Item> target) {
            for (int i = 0; i < source.size(); i++) {
                target.add(source.get(i));
            }

            int maxIndex = target.size();
            for (int i = 0; i < maxIndex; i++) {
                iterationOrder.swapElements(StdRandom.uniform(maxIndex), StdRandom.uniform(maxIndex));
            }
        }
    }

    private static class ResizingArray<Item> {

        private Item[] array;

        private int size;

        ResizingArray(int capacity) {
            array = (Item[]) new Object[capacity];
        }

        Item get(int index) {
            validateIndex(index);
            return array[index];
        }

        void set(int index, Item element) {
            validateIndex(index);
            array[index] = element;
        }

        void add(Item element) {
            int newIndex = size;

            if (capacity() <= newIndex) {
                resize(capacity() * 2);
            }

            array[newIndex] = element;
            size++;
        }

        Item remove(int index) {
            validateIndex(index);

            Item removedItem = array[index];
            swapElements(index, --size);
            return removedItem;
        }

        int size() {
            return size;
        }

        int capacity() {
            return array.length;
        }

        void swapElements(int from, int to) {
            Item temporary = array[to];
            array[to] = array[from];
            array[from] = temporary;
        }

        private void resize(int newCapacity) {
            if (size() <= newCapacity) {
                Item[] resizedArray = (Item[]) new Object[newCapacity];
                System.arraycopy(array, 0, resizedArray, 0, size());
                array = resizedArray;
            } else {
                throw new IllegalArgumentException("Capacity should be equal or more than " + size());
            }
        }

        private void validateIndex(int index) {
            if (0 > index || index >= size) {
                throw new IndexOutOfBoundsException("" + index);
            }
        }
    }
}
