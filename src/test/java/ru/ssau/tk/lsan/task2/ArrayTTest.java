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

    @Test
    public void testGetFirstIndexOf() {
        final Integer[] arrInt = new Integer[]{2,4,13,4};
        final Double[] arrDouble = new Double[]{1d,5d,2d,2d,2d,40d};

        assertEquals(test.getFirstIndexOf(arrInt,arrInt[1]),1,delta);
        assertEquals(test.getFirstIndexOf(arrDouble,arrDouble[2]),2,delta);
    }

    @Test
    public void testSwapFirstAndLastElement() {
        final Integer[] arrInt = new Integer[]{2,4,13,4};
        final Integer[] arrIntAns = new Integer[]{4,4,13,2};
        final Double[] arrDouble = new Double[]{1d,5d,2d,40d};
        final Double[] arrDoubleAns = new Double[]{40d,5d,2d,1d};
        test.swapFirstAndLastElement(arrInt);
        test.swapFirstAndLastElement(arrDouble);
        for(int i = 0; i<4;i++){
            assertEquals(arrInt[i],arrIntAns[i],delta);
            assertEquals(arrDouble[i],arrDoubleAns[i],delta);
        }

    }
}