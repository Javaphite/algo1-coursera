package com.javaphite.algo1.stacksandqueues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private ResizingArray<Item> queue;

    private static final int DEFAULT_INITIAL_CAPACITY = 10;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = new ResizingArray<>(DEFAULT_INITIAL_CAPACITY);
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return 0 == queue.size();
    }

    // return the number of items on the randomized queue
    public int size() {
        return queue.size();
    }

    // add the item
    public void enqueue(Item item) {
        queue.add(item);
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty!");
        }
        return queue.remove(StdRandom.uniform(size()));
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty!");
        }
        return queue.get(StdRandom.uniform(size()));
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>(queue);
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

    private static class RandomizedQueueIterator<Item> implements Iterator<Item> {

        private ResizingArray<Item> iterationOrder;

        RandomizedQueueIterator(ResizingArray<Item> items) {
            iterationOrder = new ResizingArray<>(items.size());
            copyItemsInRandomOrder(items, iterationOrder);
        }

        private void copyItemsInRandomOrder(final ResizingArray<Item> source, ResizingArray<Item> target) {
            synchronized (this) {
                for (int i = 0; i < source.size(); i++) {
                    target.add(source.get(i));
                }
            }

            int maxIndex = target.size();
            for (int i = 0; i < maxIndex; i++) {
                iterationOrder.swapElements(StdRandom.uniform(maxIndex), StdRandom.uniform(maxIndex));
            }
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
            return iterationOrder.remove(0);
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

        public void set(int index, Item element) {
            validateIndex(index);
            array[index] = element;
        }

        void add(Item element) {
            int newIndex = size;

            if (capacity() < newIndex) {
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

        private void resize(int newCapacity) {
            if (size() <= newCapacity) {
                Item[] resizedArray = (Item[]) new Object[newCapacity];
                System.arraycopy(array, 0, resizedArray, 0, size());
                array = resizedArray;
            } else {
                throw new IllegalArgumentException("Capacity should be equal or more than " + size());
            }
        }

        int size() {
            return size;
        }

        int capacity() {
            return array.length;
        }

        private void validateIndex(int index) {
            if (0 > index || index >= size) {
                throw new IndexOutOfBoundsException("" + index);
            }
        }

        void swapElements(int from, int to) {
            Item temporary = array[to];
            array[to] = array[from];
            array[from] = temporary;
        }
    }
}
