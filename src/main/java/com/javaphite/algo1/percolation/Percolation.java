package com.javaphite.algo1.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public class Percolation {

    private static final int SITES_OFFSET = 2;

    private static final int BLOCKED = 0;

    private static final int OPENED = 1;

    private static final int FULL = 2;

    private int[] sites;

    private WeightedQuickUnionUF unionFind;

    private int sourceSite = 0;

    private int destinationSite = 1;

    private int totalSitesNumber;

    private int openSitesNumber;

    private int systemSize;

    private boolean systemPercolated;

    public Percolation(int n) {
        systemSize = n;
        totalSitesNumber = SITES_OFFSET + (n * n);
        sites = new int[totalSitesNumber];
        unionFind = new WeightedQuickUnionUF(totalSitesNumber);
        Arrays.fill(sites, BLOCKED);
        sites[sourceSite] = OPENED;
        sites[destinationSite] = OPENED;
    }

    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            sites[getFlatIndex(row, col)] = OPENED;
            updateSiteConnections(row, col);
            openSitesNumber++;
        }
    }

    public boolean isOpen(int row, int col) {
        int flatIndex = getFlatIndex(row, col);
        return sites[flatIndex] == OPENED;
    }

    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) {
            return false;
        }

        if (unionFind.connected(getFlatIndex(row, col), sourceSite)) {
            return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        return openSitesNumber;
    }

    public boolean percolates() {
        return unionFind.connected(sourceSite, destinationSite);
    }

    private int getFlatIndex(int row, int col) {
        return SITES_OFFSET + (row - 1) * systemSize + (col - 1);
    }

    private void updateSiteConnections(int row, int col) {
        if (row == 1) {
            unionFind.union(getFlatIndex(row, col), sourceSite);
        }
        connectWithNeighbors(row, col);
        checkAttachedToDestination(row, col);
    }

    private void connectWithNeighbors(int row, int col) {
        for (Neighbour neighbour : Neighbour.values()) {
            int neighbourRow = row + neighbour.rowOffset;
            int neighbourCol = col + neighbour.columnOffset;

            if (isValidIndex(neighbourRow) && isValidIndex(neighbourCol)) {
                if (isOpen(neighbourRow, neighbourCol)) {
                    int neighbourFlatIndex = getFlatIndex(neighbourRow, neighbourCol);
                    int flatIndex = getFlatIndex(row, col);
                    unionFind.union(flatIndex, neighbourFlatIndex);
                }
            }
        }
    }

    private void checkAttachedToDestination(int row, int col) {
        if (row == systemSize) {
            unionFind.union(getFlatIndex(row, col), destinationSite);
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
