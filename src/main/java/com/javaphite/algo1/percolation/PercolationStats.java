package com.javaphite.algo1.percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    // private Percolation percolation;

    private int trials;

    private int systemSize;

    private double[] trialResults;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        this.trialResults = new double[trials];
        systemSize = n;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trialResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trialResults);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - stddev();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + stddev();
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);
        stats.runSimulation();

        StdOut.printf("mean = %f\n", stats.mean());
        // real treshold for 2x2 system
        StdOut.println((2 * 0.33 + 3 * 0.67) / 4);
        StdOut.printf("stddev = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n", stats.confidenceLo(), stats.confidenceHi());

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
