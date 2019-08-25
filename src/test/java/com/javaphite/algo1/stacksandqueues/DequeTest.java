package com.javaphite.algo1.stacksandqueues;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DequeTest {

    private Deque<Character> sut;

    @BeforeEach
    void setUp() {
        sut = new Deque<>();
    }

    @Test
    void isEmptyShouldReturnTrueOnEmptyDeque() {
        assertTrue(sut.isEmpty());
    }

    @Test
    void isEmptyShouldReturnFalseOnNonEmptyDeque() {
        sut.addLast('A');
        assertFalse(sut.isEmpty());
    }

    @Test
    void sizeShouldReturn2() {
        sut.addLast('A');
        sut.addFirst('B');

        assertEquals(2, sut.size());
    }

    @Test
    void removeFirstAfterAddFirstShouldReturnTheSameItem() {
        Character expectedResult = 'A';

        sut.addFirst('A');
        Character actualResult = sut.removeFirst();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void removeLastAfterAddLastShouldReturnTheSameItem() {
        Character expectedResult = 'B';

        sut.addLast('B');
        Character actualResult = sut.removeLast();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void iteratorShouldTraverseThroughDequeFromHeadToTail() {
        sut.addFirst('B');
        sut.addLast('C');
        sut.addFirst('A');

        StringBuilder result = new StringBuilder();
        sut.forEach(result::append);

        assertEquals("ABC", result.toString());
    }
}