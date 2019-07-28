package com.javaphite.algo1.percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    public static final String MEAN_MESSAGE_TEMPLATE = "mean = %f\n";

    public static final String STD_DEVIATION_MESSAGE_TEMPLATE = "stddev = %f\n";

    public static final String CONFIDENCE_MESSAGE_TEMPLATE = "95%% confidence interval = [%f, %f]\n";

    private int trials;

    private int systemSize;

    private double[] trialResults;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Not valid arguments: n=" + n + ", T=" + trials);
        }
        this.trials = trials;
        this.trialResults = new double[trials];
        systemSize = n;
    }

    public double mean() {
        return StdStats.mean(trialResults);
    }

    public double stddev() {
        return StdStats.stddev(trialResults);
    }

    public double confidenceLo() {
        return mean() - stddev();
    }

    public double confidenceHi() {
        return mean() + stddev();
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);
        stats.runSimulation();

        StdOut.printf(MEAN_MESSAGE_TEMPLATE, stats.mean());
        StdOut.printf(STD_DEVIATION_MESSAGE_TEMPLATE, stats.stddev());
        StdOut.printf(CONFIDENCE_MESSAGE_TEMPLATE, stats.confidenceLo(), stats.confidenceHi());

    }

    private void runSimulation() {
        for (int i = 0; i < trials; i++) {
            Percolation system = new Percolation(systemSize);
            while (!system.percolates()) {
                int randomRow = StdRandom.uniform(systemSize) + 1;
                int randomCol = StdRandom.uniform(systemSize) + 1;

                system.open(randomRow, randomCol);
            }
            trialResults[i] = system.numberOfOpenSites() / (double) (systemSize * systemSize);
        }
    }
}
