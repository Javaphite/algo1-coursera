package com.javaphite.algo1.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int SITES_OFFSET = 2;

    private static final boolean BLOCKED = false;

    private static final boolean OPENED = true;

    private static final int SOURCE_SITE = 0;

    private static final int DESTINATION_SITE = 1;

    private final int systemSize;

    private int openSitesNumber;

    private boolean[] sites;

    private WeightedQuickUnionUF unionFind;

    private WeightedQuickUnionUF visualUnionFind;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("System size could not be less or equal to 0!");
        }

        systemSize = n;
        int totalSitesNumber = SITES_OFFSET + (n * n);
        sites = new boolean[totalSitesNumber];
        unionFind = new WeightedQuickUnionUF(totalSitesNumber);
        visualUnionFind = new WeightedQuickUnionUF(totalSitesNumber);

        for (int i = 0; i < totalSitesNumber; i++) {
            sites[i] = BLOCKED;
        }
    }

    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            sites[getFlatIndex(row, col)] = OPENED;
            updateSiteConnections(row, col);
            openSitesNumber++;
        }
    }

    public boolean isOpen(int row, int col) {
        validateCoordinates(row, col);

        int flatIndex = getFlatIndex(row, col);
        return sites[flatIndex];
    }

    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) {
            return false;
        }

        if (visualUnionFind.connected(getFlatIndex(row, col), SOURCE_SITE)) {
            return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        return openSitesNumber;
    }

    public boolean percolates() {
        return unionFind.connected(SOURCE_SITE, DESTINATION_SITE);
    }

    private int getFlatIndex(int row, int col) {
        return SITES_OFFSET + (row - 1) * systemSize + (col - 1);
    }

    private void updateSiteConnections(int row, int col) {
        connectFirstRowToTop(row, col);
        connectWithNeighbors(row, col);
        connectLastRowToBottom(row, col);
    }

    private void connectFirstRowToTop(int row, int col) {
        if (row == 1) {
            unionFind.union(getFlatIndex(row, col), SOURCE_SITE);
            visualUnionFind.union(getFlatIndex(row, col), SOURCE_SITE);
        }
    }

    private void connectLastRowToBottom(int row, int col) {
        if (row == systemSize) {
            unionFind.union(getFlatIndex(row, col), DESTINATION_SITE);
        }
    }

    private void connectWithNeighbors(int row, int col) {
        for (Neighbour neighbour : Neighbour.values()) {
            int neighbourRow = row + neighbour.rowOffset;
            int neighbourCol = col + neighbour.columnOffset;

            if (isValidIndex(neighbourRow)
                    && isValidIndex(neighbourCol)
                    && isOpen(neighbourRow, neighbourCol)) {
                int neighbourFlatIndex = getFlatIndex(neighbourRow, neighbourCol);
                int flatIndex = getFlatIndex(row, col);
                unionFind.union(flatIndex, neighbourFlatIndex);
                visualUnionFind.union(flatIndex, neighbourFlatIndex);
            }
        }
    }

    public static void main(String[] args) {
        // No custom test client provided
    }

    private void validateCoordinates(int row, int col) {
        if (!isValidIndex(row) || !isValidIndex(col)) {
            throw new IllegalArgumentException("Invalid site coordinates: row =" + row + ", col= " + col);
        }
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
