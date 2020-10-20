package ru.ssau.tk.lsan.task1;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PointTest {

    @Test
    public void testTestToString() {
        Point testPoint = new Point(1,1,1);
        assertEquals(testPoint.toString(),"[1.0, 1.0, 1.0]");
    }
}