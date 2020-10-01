package ru.ssau.tk.lsan.task2;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ArrayTest {
    Array testArr = new Array();

    @Test
    public void testNewDoubleArray() {

        assertEquals(testArr.newDoubleArray(2).length, 2);
        assertEquals(testArr.newDoubleArray(0).length, 0);
    }
}