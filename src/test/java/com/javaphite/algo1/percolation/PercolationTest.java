package com.javaphite.algo1.percolation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UncheckedIOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class PercolationTest {

    private static final String TEST_INPUTS_PATH = "/home/javaphite/IdeaProjects/algo1-coursera/src/test/resource/inputs/";

    private static final String TESTCASES_LIST_FILE = "/inputs/testcaseslist.csv";

    private int n;

    private List<int[]> sitesToOpen;

    private Percolation sut;

    @ParameterizedTest
    @CsvFileSource(resources = TESTCASES_LIST_FILE)
    void shouldReturnResultsAccordingToTestCaseList(String filename, String expected) {
        setUpFromFile(TEST_INPUTS_PATH + filename);
        boolean expectedResult = Boolean.parseBoolean(expected);

        boolean actualResult = sut.percolates();

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void methodsShouldThrowIllegalArgumentExceptionOnBadCoordinates() {
        sut = new Percolation(5);

        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> sut.open(10, 10)),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> sut.isOpen(10, 10)),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> sut.isFull(10, 10))
        );
    }

    @Test
    void constructorShouldThrowIllegalArgumentExceptionForNonPositiveArgument() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Percolation(0));
    }

    private void openSites(List<int[]> sites) {
        for (int[] site : sites) {
            sut.open(site[0], site[1]);
        }
    }

    private void setUpFromFile(String file) {
        try (Scanner scanner = new Scanner(new File(file))) {

            if (!scanner.hasNextInt()) {
                throw new IllegalArgumentException("File is empty!");
            }

            n = scanner.nextInt();
            sitesToOpen = new LinkedList<>();
            while (scanner.hasNextLine() && scanner.hasNextInt()) {
                int[] siteCoordinates = new int[2];
                siteCoordinates[0] = scanner.nextInt();
                siteCoordinates[1] = scanner.nextInt();
                sitesToOpen.add(siteCoordinates);
            }

            sut = new Percolation(n);
            openSites(sitesToOpen);
        } catch (FileNotFoundException exception) {
            throw new UncheckedIOException(exception);
        }
    }
}
