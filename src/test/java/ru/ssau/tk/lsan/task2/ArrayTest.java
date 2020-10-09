package ru.ssau.tk.lsan.task2;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ArrayTest {
    Array testArr = new Array();
    protected final static int size = 6;
    protected final static double delta = 0.001;

    @Test
    public void testNewEmptyDoubleArray() {
        assertEquals(testArr.newEmptyDoubleArray(2).length, 2);
        assertEquals(testArr.newEmptyDoubleArray(0).length, 0);
    }

    @Test
    public void testNewFilledDoubleArray() {
        final Double[] newArr = new Array().newFilledDoubleArray(size);
        assertEquals(newArr[0], 2d, delta);
        assertEquals(newArr[size - 1], 2d, delta);
        for (int i = 1; i < size - 2; i++) {
            assertEquals(newArr[i], 1d, delta);
        }
    }

    @Test
    public void testNewOddArray() {
        final Double[] newOddArr = new Array().newOddArray(size);
        for (int i = 0; i < size; i++) {
            assertEquals(newOddArr[i], 2*i+1d, delta);
        }
    }

    @Test
    public void testNewDescendingEvenArray() {
        final Double[] newEvenArr = new Array().newDescendingEvenArray(size);
        for (int i = size-1; i > -1; i--) {
            assertEquals(newEvenArr[i], 2*i+2, delta);
        }
    }

    @Test
    public void testNewSqrIndexArray() {
        final Double[] newSqrArr = new Array().newSqrIndexArray(size);
        final Double[] sqrArr = new Double[]{0d, 1d, 4d, 9d, 16d, 25d};
        for (int i = 0; i < size; i++) {
            assertEquals(newSqrArr[i], sqrArr[i], delta);
        }
    }

    @Test
    public void testNewFibonacciArray() {
        final Double[] newFibArr = new Array().newFibonacciArray(6);
        final Double[] fibArr = new Double[]{1d,1d,2d,3d,5d,8d};
        for (int i = 0; i < size; i++) {
            assertEquals(newFibArr[i], fibArr[i], delta);
        }
    }

    @Test
    public void testNewQuadraticEquationArray() {
        final Double[] newQuad = new Array().newQuadraticEquationArray(1d,2d,1d);
        final Double[] quad = new Double[]{1d};
        assertEquals(newQuad.length,quad.length,delta);
        for (int i = 0; i <newQuad.length; i++) {
            assertEquals(newQuad[i], quad[i], delta);
        }
    }
}