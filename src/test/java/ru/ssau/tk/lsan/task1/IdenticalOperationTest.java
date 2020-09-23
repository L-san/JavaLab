package ru.ssau.tk.lsan.task1;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class IdenticalOperationTest {
    Operation testFunc = new IdenticalOperation();

    @Test
    public void testApply() {
        assertEquals(testFunc.apply(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY);
        assertEquals(testFunc.apply(Double.NEGATIVE_INFINITY), Double.NEGATIVE_INFINITY);
        assertEquals(testFunc.apply(-1),-1,0.001);
    }
}