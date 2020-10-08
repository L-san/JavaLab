package ru.ssau.tk.lsan.task2;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ArrayTest {
    Array testArr = new Array();
    protected final static int size = 6;
    protected final static double delta = 0.001;
    protected final Double[] newArr = new Array().newFilledDoubleArray(size);
    protected final Double[] newOddArr = new Array().newOddArray(size);
    protected final Double[] newEvenArr = new Array().newDescendingEvenArray(size);
    protected final Double[] newFibArr = new Array().newFibonacciArray(6);
    protected final Double[] fibArr = new Double[]{1d,1d,2d,3d,5d,8d};
    protected final Double[] newSqrArr = new Array().newSqrIndexArray(size);
    protected final Double[] sqrArr = new Double[]{0d, 1d, 4d, 9d, 16d, 25d};

    @Test
    public void testNewEmptyDoubleArray() {
        assertEquals(testArr.newEmptyDoubleArray(2).length, 2);
        assertEquals(testArr.newEmptyDoubleArray(0).length, 0);
    }

    @Test
    public void testNewFilledDoubleArray() {
        assertEquals(newArr[0], 2d, delta);
        assertEquals(newArr[size - 1], 2d, delta);
        for (int i = 1; i < size - 2; i++) {
            assertEquals(newArr[i], 1d, delta);
        }
    }

    @Test
    public void testNewOddArray() {
        for (int i = 0; i < size; i++) {
            assertEquals(newOddArr[i], 2*i+1d, delta);
        }
    }

    @Test
    public void testNewDescendingEvenArray() {
        for (int i = size-1; i > -1; i--) {
            assertEquals(newEvenArr[i], 2*i+2, delta);
        }
    }

    @Test
    public void testNewFibonacciArray() {
        for (int i = 0; i < size; i++) {
            assertEquals(newSqrArr[i], sqrArr[i], delta);
        }
    }
}