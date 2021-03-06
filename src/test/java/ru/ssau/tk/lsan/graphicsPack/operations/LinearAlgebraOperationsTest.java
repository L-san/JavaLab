package ru.ssau.tk.lsan.graphicsPack.operations;

import org.ejml.simple.SimpleMatrix;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class LinearAlgebraOperationsTest {
    LinearAlgebraOperations test = new LinearAlgebraOperations();
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
        SimpleMatrix mat1 = new SimpleMatrix(m1);
        SimpleMatrix mat2 = new SimpleMatrix(m2);
        SimpleMatrix ans1 = mat1.mult(mat2);
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
        double[] q_omega_dot = new double[]{1d,2d,3d,4d};
        double delta_T = 2;
        ans = test.matrixMultiplication(q_omega_dot, delta_T);
        ansArr = new double[]{2, 4, 6,8};
        for(int i = 0;i<3;i++){
            assertEquals(ans[i],ansArr[i],delta);
        }
    }

    @Test
    public void testMatrixSum() {
        double[] m1 = new double[]{0, 5, 1};
        double[] m2 = new double[]{1,2,3};
        double[][] m12 = new double[][]{{0, 5},{ 1,1},{2,3}};
        double[] ans = test.matrixSum(m1,m2);
        double[] ansArr = new double[]{1,7,4};
        for(int i = 0; i<3;i++){
            assertEquals(ans[i],ansArr[i],delta);
        }
        SimpleMatrix g = new SimpleMatrix(3, 1, true, m1);
        SimpleMatrix m = new SimpleMatrix(3, 1, true, m2);
        SimpleMatrix initVec =new SimpleMatrix(3,3,true,new double[]{0, 1, 2,10,11,12,20,21,22});
        for(int i = 0; i<3;i++){
            for(int j = 0; j<3;j++){
                //assertEquals(initVec.get(i,j),m12[i][j]);
                System.out.print(" "+initVec.get(i,j));
            }
            System.out.print('\n');
        }
        SimpleMatrix mat1 = new SimpleMatrix(3,1,true,m1);

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
    public void testTransponse() {
    }

    @Test
    public void testInv() {
    }

    @Test
    public void testPuassonsEq(){
        double[] q_omega_dot = new double[]{1d,2d,3d,4d};
        double delta_T = 2;
        double[] ans = test.matrixSum(q_omega_dot, test.matrixMultiplication(q_omega_dot, delta_T));
        double[] ansArr = new double[]{3, 6, 9,12};
        for(int i = 0;i<3;i++){
            assertEquals(ans[i],ansArr[i],delta);
        }
        System.out.println(Math.acos(1));
    }

}