package ru.ssau.tk.lsan.task1;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PointsTest {

    @Test
    public void testOpposite() {
        Point testPoint = new Point(1,1,1);
        testPoint = Points.opposite(testPoint);
        assertEquals(testPoint.X,-1);
        assertEquals(testPoint.Y,-1);
        assertEquals(testPoint.Z,-1);
    }
}