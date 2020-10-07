package ru.ssau.tk.lsan.task2;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ArrayTest {
    Array testArr = new Array();
    protected final static int size = 6;
    protected final static double delta = 0.001;
    protected final Double[] newArr = new Array().newFilledDoubleArray(size);
    protected final Double[] newOddArr = new Array().newOddArray(size);
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
}