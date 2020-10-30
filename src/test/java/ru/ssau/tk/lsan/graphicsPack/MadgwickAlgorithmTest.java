package ru.ssau.tk.lsan.graphicsPack;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MadgwickAlgorithmTest {

    protected final MadgwickAlgorithm test = new MadgwickAlgorithm();
    protected final double[] xArr = new double[]{1d, 2d, 3d};
    protected final double[] yArr = new double[]{4d, 5d, 6d};
    protected final double delta = 0.001;

    @Test
    public void testDot() {
        assertEquals(test.dot(xArr, xArr), 14, delta);
        assertEquals(test.dot(xArr, yArr), 32, delta);
        assertEquals(test.dot(yArr, yArr), 77, delta);
    }

    @Test
    public void testCross() {
        double[] oneArr = test.cross(xArr, xArr);
        double[] twoArr = test.cross(xArr, yArr);
        double[] twoAns = new double[]{-3d, 6d, -3d};
        for (int i = 0; i < 3; i++) {
            assertEquals(oneArr[i], 0d, delta);
            assertEquals(twoArr[i], twoAns[i], delta);
        }
    }

    @Test
    public void testQuat_mult() {
    }
}