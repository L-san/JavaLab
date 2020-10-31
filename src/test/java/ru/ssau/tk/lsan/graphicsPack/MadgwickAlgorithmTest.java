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
        double[][] m1 = new double[][]{{1, 2, 3}, {4, 5, 7}};
        double[][] m2 = new double[][]{{0, 5}, {6, 2}, {3, 1}};
        double[][] ans = new double[][]{{21, 12}, {51, 37}};
        double[][] ansArr = test.matrixMultiplication(m1, m2, 2, 3, 2);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(ansArr[i][j], ans[i][j], delta);
            }
        }
    }

    @Test
    public void testMatrixMultiplication2() {
        double[][] m1 = new double[][]{{1, 2, 3, 4, 5, 6}, {7, 8, 9, 10, 11, 12}, {13, 14, 15, 16, 17, 18}, {19, 20, 21, 22, 23, 24}};
        double[] m2 = new double[]{0, 5, 1, 2, 3, 7};
        double[] ans = test.matrixMultiplication(m1, m2);
        double[] ansArr = new double[]{78,186,294,402};
        for(int i = 0;i<4;i++){
            assertEquals(ans[i],ansArr[i],delta);
        }
    }

    @Test
    public void testMatrixMultiplication3() {
        double[] m1 = new double[]{0, 5, 1};
        double[] ans = test.matrixMultiplication(m1, 2d);
        double[] ansArr = new double[]{0, 10, 2};
        for(int i = 0;i<3;i++){
            assertEquals(ans[i],ansArr[i],delta);
        }
    }

    @Test
    public void testMatrixSum() {
        double[] m1 = new double[]{0, 5, 1};
        double[] m2 = new double[]{1,2,3};
        double[] ans = test.matrixSum(m1,m2);
        double[] ansArr = new double[]{1,7,4};
        for(int i = 0; i<3;i++){
            assertEquals(ans[i],ansArr[i],delta);
        }
    }

    @Test
    public void testMatrixSum2() {
        double[] m1 = new double[]{0, 5, 1};
        double[] ans = test.matrixSum(m1,2d,3);
        double[] ansArr = new double[]{2,7,3};
        for(int i = 0; i<3;i++){
            assertEquals(ans[i],ansArr[i],delta);
        }
    }

    @Test
    public void testNormalizeVector() {
        double[] m1 = new double[]{0, 5, 1};
        double[] ans = test.normalizeVector(m1);
        double[] ansArr = new double[]{0, 0.9806, 0.1961};
        for(int i = 0; i<3;i++){
            assertEquals(ans[i],ansArr[i],delta);
        }
    }

    @Test
    public void testMatrixDivision() {
        double[] m1 = new double[]{0, 5, 1};
        double[] m2 = new double[]{1,2,3};
        double[] ans = test.matrixDivision(m1,m2,3);
        double[] ansArr = new double[]{-1,3,-2};
        for(int i = 0; i<3;i++){
            assertEquals(ans[i],ansArr[i],delta);
        }
    }

    @Test
    public void testCalculatePosition() {
        double dzta = 8.660254037844386e-04;
        double bta = 8.660254037844386e-04;
        double[] q_est = new double[]{1,0,0,0};
        double omega_eps_prev = 0;
        double delta_T = 0.001;

        MadgwickAlgorithm realTest = new MadgwickAlgorithm(q_est,omega_eps_prev,bta,dzta,delta_T);
        double[] q = realTest.getQ_est();
        realTest.calculatePosition(new double[]{1,0,0}, new double[]{0,0,1}, new double[]{0,0,0});
        double[] qCalc = realTest.getQ_est();
        for(int i =0;i<4;i++){
            assertEquals(q[i],q_est[i],delta);
            assertEquals(qCalc[i],q_est[i],delta);
        }
    }

}