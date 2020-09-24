package ru.ssau.tk.lsan.task1;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class SqrtOperationTest {
    Operation testFunc = new SqrtOperation();

    @Test
    public void testApply() {
        assertEquals(testFunc.apply(4), 2, 0.001);
        assertEquals(testFunc.apply(Double.POSITIVE_INFINITY),Double.POSITIVE_INFINITY);
        assertNotEquals(testFunc.apply(Double.NEGATIVE_INFINITY),Double.NEGATIVE_INFINITY);
        assertEquals(testFunc.apply(Double.NaN),Double.NaN);
        assertNotEquals(testFunc.apply(-1),1,0.001);
    }
}