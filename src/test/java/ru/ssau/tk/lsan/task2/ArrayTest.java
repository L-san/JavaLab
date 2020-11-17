package ru.ssau.tk.lsan.task2;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ArrayTest {
    Array testArr = new Array();
    protected final static int size = 6;
    protected final static double delta = 0.001;

    @Test
    public void testNewEmptyDoubleArray() {
        assertEquals(testArr.newEmptyDoubleArray(2).length, 2);
        assertEquals(testArr.newEmptyDoubleArray(0).length, 0);
    }

    @Test
    public void testNewFilledDoubleArray() {
        final Double[] newArr = new Array().newFilledDoubleArray(size);
        assertEquals(newArr[0], 2d, delta);
        assertEquals(newArr[size - 1], 2d, delta);
        for (int i = 1; i < size - 2; i++) {
            assertEquals(newArr[i], 1d, delta);
        }
    }

    @Test
    public void testNewOddArray() {
        final Double[] newOddArr = new Array().newOddArray(size);
        for (int i = 0; i < size; i++) {
            assertEquals(newOddArr[i], 2*i+1d, delta);
        }
    }

    @Test
    public void testNewDescendingEvenArray() {
        final Double[] newEvenArr = new Array().newDescendingEvenArray(size);
        for (int i = size-1; i > -1; i--) {
            assertEquals(newEvenArr[i], 2*i+2, delta);
        }
    }

    @Test
    public void testNewSqrIndexArray() {
        final Double[] newSqrArr = new Array().newSqrIndexArray(size);
        final Double[] sqrArr = new Double[]{0d, 1d, 4d, 9d, 16d, 25d};
        for (int i = 0; i < size; i++) {
            assertEquals(newSqrArr[i], sqrArr[i], delta);
        }
    }

    @Test
    public void testNewFibonacciArray() {
        final Double[] newFibArr = new Array().newFibonacciArray(6);
        final Double[] fibArr = new Double[]{1d,1d,2d,3d,5d,8d};
        for (int i = 0; i < size; i++) {
            assertEquals(newFibArr[i], fibArr[i], delta);
        }
    }

    @Test
    public void testNewQuadraticEquationArray() {
        final Double[] newQuad = new Array().newQuadraticEquationArray(1d,2d,1d);
        final Double[] newQuadZeroA = new Array().newQuadraticEquationArray(0d,2d,4d);
        final Double[] quad = new Double[]{-1d};
        final Double[] quadZeroA = new Double[]{-2d};
        assertEquals(newQuad.length,quad.length,delta);
        assertEquals(newQuadZeroA.length,quadZeroA.length,delta);
        for (int i = 0; i <newQuad.length; i++) {
            assertEquals(newQuad[i], quad[i], delta);
        }
        for (int i = 0; i <newQuadZeroA.length; i++) {
            assertEquals(newQuadZeroA[i], quadZeroA[i], delta);
        }
    }

    @Test
    public void testNewNotDivisibleBy3Array() {
        final Double[] newArr = new Array().newNotDivisibleBy3Array(6);
        final Double[] DivArr = new Double[]{1d,5d,7d,11d,13d,17d};
        for(int i = 0; i<6; i++){
            assertEquals(newArr[i], DivArr[i], delta);
        }
    }

    @Test
    public void testNewAriphmeticProgressionArray() {
        final Double[] ariphArr = new Array().newArithmeticProgressionArray(5,1,2);
        final Double[] ariphCheck = new Double[]{1d,3d,5d,7d,9d};
        for(int i = 0; i<5; i++){
            assertEquals(ariphArr[i], ariphCheck[i], delta);
        }
    }

    @Test
    public void testMakeOppositeSign() {
        final Double[] arr = new Double[]{-1d,3d,-5d,7d,-9d};
        final Array arrType = new Array();
        arrType.makeOppositeSign(arr);
        final Double[] check = new Double[]{1d,-3d,5d,-7d,9d};
        for(int i = 0; i<5; i++){
            assertEquals(arr[i],check[i]);
        }

    }

    @Test
    public void testNewGeometricProgressionArray() {
        final Double[] ariphArr = new Array().newGeometricProgressionArray(5,1,2);
        final Double[] ariphCheck = new Double[]{1d,2d,4d,8d,16d};
        for(int i = 0; i<5; i++){
            assertEquals(ariphArr[i], ariphCheck[i], delta);
        }
    }

    @Test
    public void testAllDivisorsOfAnInteger() {
        final Double[] arr = new Double[]{2d,3d,13d};
        final Double[] ans = new Array().allDivisorsOfAnInteger(78);
        for(int i = 0;i<3;i++){
            assertEquals(ans[i],arr[i],delta);
        }
    }

    @Test
    public void testIsInArray() {
        final Double[] arr = new Double[]{2d,3d,13d};
        for(int i = 0;i<3;i++){
            assertTrue(new Array().isInArray(arr[i],arr));
        }
        assertFalse(new Array().isInArray(5d,arr));
    }
}