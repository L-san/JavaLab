package ru.ssau.tk.lsan.task2;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ArrayTTest {
    ArrayT test = new ArrayT();
    final double delta = 0.001;

    @Test
    public void testOccursMostOften() {
        final Integer[] arrInt = new Integer[]{2,4,13,4};
        final Double[] arrDouble = new Double[]{1d,5d,2d,2d,2d,40d};

        assertEquals(test.occursMostOften(arrInt),4,delta);
        assertEquals(test.occursMostOften(arrDouble),2d,delta);
    }
}