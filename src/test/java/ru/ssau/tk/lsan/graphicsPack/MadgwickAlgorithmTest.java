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
        double[] oneQuat = new double[]{1d, 0d, 0d, 0d};
        double[] twoQuat = new double[]{0.1601, 0.3203, 0.4804, 0.8006};
        double[] ans = new double[]{0.1601, 0.3203, 0.4804, 0.8006};
        double[] ansArr = test.quat_mult(oneQuat, twoQuat);
        for (int i = 0; i < 4; i++) {
            assertEquals(ans[i], ansArr[i], delta);
        }

    }

    @Test
    public void testQuat_conj() {
        double[] twoQuat = new double[]{0.1601, 0.3203, 0.4804, 0.8006};
        double[] ansArr = test.quat_conj(twoQuat);
        double[] ans = new double[]{0.1601, -0.3203, -0.4804, -0.8006};
        for (int i = 0; i < 4; i++) {
            assertEquals(ansArr[i], ans[i], delta);
        }
    }

    @Test
    public void testMatrixMultiplication() {
        double[][] m1 = new double[][]{{1,2,3},{4,5,7}};
        double[][] m2 = new double[][]{{0,5},{6,2},{3,1}};
        double[][] ans = new double[][]{{21,12},{51,37}};
        double[][] ansArr = test.matrixMultiplication(m1,m2,2,3,2);
        for(int i = 0;i<2;i++){
            for(int j = 0; j<2;j++){
                assertEquals(ansArr[i][j],ans[i][j],delta);
            }
        }
    }
}