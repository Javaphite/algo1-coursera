package com.javaphite.algo1.stacksandqueues;

import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    private static final String INVALID_ARGUMENTS_MSG =
            "Arguments should contain int k (0 <= k <= n) as first argument, followed with n strings!";

    public static void main(String[] args) {
        int k = getNumberOfStringsToPrint(args);

        if (-1 == k) {
            throw new IllegalArgumentException(INVALID_ARGUMENTS_MSG);
        }

        RandomizedQueue<String> queue = new RandomizedQueue<>();
        for (int i = 1; i < args.length; i++) {
            queue.enqueue(args[i]);
        }

        for (int i=0; i < k && !queue.isEmpty(); i++) {
            StdOut.println(queue.dequeue());
        }

    }

    private static int getNumberOfStringsToPrint(String[] args) {
        try {
            return Integer.parseInt(args[0]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException exception) {
            return -1;
        }
    }

}
