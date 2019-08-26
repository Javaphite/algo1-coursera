package com.javaphite.algo1.stacksandqueues;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomizedQueueTest {

    private RandomizedQueue<Integer> sut = new RandomizedQueue<>();

    @BeforeEach
    void setUp() {
        sut.enqueue(1);
    }

    @Test
    void sizeShouldReturn1() {
        assertEquals(1, sut.size());
    }

    @Test
    void isEmptyShouldReturnFalseOnRecentlyCreated0ElementQueue() {
        sut.dequeue();
        assertTrue(sut.isEmpty());
    }

    @Test
    void dequeueShouldReturnLesserSizeAfterRemove() {
        int initialSize = sut.size();
        sut.dequeue();
        int finalSize = sut.size();

        assertEquals(initialSize - 1, finalSize);
    }

    @Test
    void enqueueShouldWorkProperlyAfterDefaultCapacityReached() {
        for (int i=0; i<10; i++) {
            sut.enqueue(i);
        }
    }

    @Test
    void iteratorTraversesOverQueueInRandomOrder() {
        String unexpectedResult = "123456";

        for (int i = 2; i <= 6; i++) {
            sut.enqueue(i);
        }

        StringBuilder firstTry = new StringBuilder();
        sut.forEach(firstTry::append);

        System.out.println(firstTry);

        assertFalse(firstTry.toString().isEmpty());
        assertEquals(unexpectedResult.length(), firstTry.toString().length());
        assertNotEquals(unexpectedResult, firstTry.toString());
    }

    @Test
    void iteratorShouldTakeIntoAccountConcurrentAccess() {
        int expectedLength = 2;

        sut.enqueue(2);
        sut.enqueue(3);
        sut.dequeue();

        StringBuilder result = new StringBuilder();
        sut.forEach(result::append);
        System.out.println(result);

        assertEquals(expectedLength, result.toString().length());
    }
}