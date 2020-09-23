package ru.ssau.tk.lsan.task1;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class IdenticalOperationTest {
    Operation posInf = new IdenticalOperation();

    @Test
    public void testApply() {
        assertEquals(posInf.apply(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY);
    }
}