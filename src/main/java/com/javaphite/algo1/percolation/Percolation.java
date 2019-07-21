package com.javaphite.algo1.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public class Percolation {

    private static final int BLOCKED = 0;

    private static final int OPENED = 1;

    private static final int FULL = 2;

    private int[] sites;

    private WeightedQuickUnionUF unionFind;

    private int totalSitesNumber;

    private int openSitesNumber;

    private int systemSize;

    private boolean systemPercolated;

    public Percolation(int n) {
        systemSize = n;
        totalSitesNumber = n * n;
        sites = new int[totalSitesNumber];
        unionFind = new WeightedQuickUnionUF(totalSitesNumber);
        Arrays.fill(sites, BLOCKED);
    }

    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            // Opened sites in top row are always full
            sites[getFlatIndex(row, col)] = row == 1 ? FULL : OPENED;
            connectWithNeighbors(row, col);
            openSitesNumber++;
        }
    }

    public boolean isOpen(int row, int col) {
        int flatIndex = getFlatIndex(row, col);
        return sites[flatIndex] >= OPENED;
    }

    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) {
            return false;
        }

        int flatIndex = getFlatIndex(row, col);
        if (sites[flatIndex] == FULL) {
            return true;
        }

        for (int i = 0; i < systemSize; i++) {
            if (unionFind.connected(i, flatIndex)) {
                return true;
            }
        }
        return false;
    }

    public int numberOfOpenSites() {
        return openSitesNumber;
    }

    // System percolates if any site in bottom row is full
    public boolean percolates() {
        // Once system become percolated - it should never
        // get back to previous state as far as we can only open sites
        // so cache that state to avoid repeated evaluations
        if (systemPercolated) {
            return systemPercolated;
        }

        if (anyFull(systemSize)) {
            systemPercolated = true;
            return systemPercolated;
        }
        return systemPercolated;
    }

    private boolean anyFull(int row) {
        for (int col = 1; col <= systemSize; col++) {
            if (isFull(row, col)) {
                return true;
            }
        }
        return false;
    }

    private int getFlatIndex(int row, int col) {
        return (row - 1) * systemSize + (col - 1);
    }

    private void connectWithNeighbors(int row, int col) {
         for (Neighbour neighbour : Neighbour.values()) {
            int neighbourRow = row + neighbour.rowOffset;
            int neighbourCol = col + neighbour.columnOffset;

            if (!isValidIndex(neighbourRow) || !isValidIndex(neighbourCol)) {
                continue;
            }

            if (isOpen(neighbourRow, neighbourCol)) {
                int neighbourFlatIndex = getFlatIndex(neighbourRow, neighbourCol);
                int flatIndex = getFlatIndex(row, col);
                unionFind.union(flatIndex, neighbourFlatIndex);
                sites[flatIndex] = sites[neighbourFlatIndex];
            }
        }
    }

    // test client (optional)
    public static void main(String[] args) {

    }

    private boolean isValidIndex(int index) {
        return (0 < index) && (index <= systemSize);
    }

    private enum Neighbour {
        NORTH(-1, 0),
        SOUTH(1, 0),
        EAST(0, 1),
        WEST(0, -1);

        int rowOffset;

        int columnOffset;

        Neighbour(int rowOffset, int columnOffset) {
            this.rowOffset = rowOffset;
            this.columnOffset = columnOffset;
        }
    }
}
