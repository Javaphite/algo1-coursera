package com.javaphite.algo1.stacksandqueues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    private static final String INVALID_ARGUMENTS_MSG =
            "Arguments should contain int k (0 <= k <= n) as argument.";

    public static void main(String[] args) {
        int k = getNumberOfStringsToPrint(args[0]);

        if (-1 == k) {
            throw new IllegalArgumentException(INVALID_ARGUMENTS_MSG);
        }

        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k && !queue.isEmpty(); i++) {
            StdOut.println(queue.dequeue());
        }

    }

    private static int getNumberOfStringsToPrint(String firstArgument) {
        try {
            return Integer.parseInt(firstArgument);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException exception) {
            return -1;
        }
    }

}
