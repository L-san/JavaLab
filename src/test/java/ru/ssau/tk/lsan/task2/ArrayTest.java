package ru.ssau.tk.lsan.task2;

import org.testng.annotations.Test;

import java.nio.ByteBuffer;

import static org.testng.Assert.*;

public class ArrayTest {
    protected final static int size = 6;
    protected final static double delta = 0.001;

    @Test
    public void testNewEmptyDoubleArray() {
        assertEquals(Array.newEmptyDoubleArray(2).length, 2);
        assertEquals(Array.newEmptyDoubleArray(0).length, 0);
    }

    @Test
    public void testNewFilledDoubleArray() {
        final Double[] newArr = Array.newFilledDoubleArray(size);
        assertEquals(newArr[0], 2d, delta);
        assertEquals(newArr[size - 1], 2d, delta);
        for (int i = 1; i < size - 2; i++) {
            assertEquals(newArr[i], 1d, delta);
        }
    }

    @Test
    public void testNewOddArray() {
        final Double[] newOddArr = Array.newOddArray(size);
        for (int i = 0; i < size; i++) {
            assertEquals(newOddArr[i], 2 * i + 1d, delta);
        }
    }

    @Test
    public void testNewDescendingEvenArray() {
        final Double[] newEvenArr = Array.newDescendingEvenArray(size);
        for (int i = size - 1; i > -1; i--) {
            assertEquals(newEvenArr[i], 2 * i + 2, delta);
        }
    }

    @Test
    public void testNewSqrIndexArray() {
        final Double[] newSqrArr = Array.newSqrIndexArray(size);
        final Double[] sqrArr = new Double[]{0d, 1d, 4d, 9d, 16d, 25d};
        for (int i = 0; i < size; i++) {
            assertEquals(newSqrArr[i], sqrArr[i], delta);
        }
    }

    @Test
    public void testNewFibonacciArray() {
        final Double[] newFibArr = Array.newFibonacciArray(6);
        final Double[] fibArr = new Double[]{1d, 1d, 2d, 3d, 5d, 8d};
        for (int i = 0; i < size; i++) {
            assertEquals(newFibArr[i], fibArr[i], delta);
        }
    }

    @Test
    public void testNewQuadraticEquationArray() {
        final Double[] newQuad = Array.newQuadraticEquationArray(1d, 2d, 1d);
        final Double[] newQuadZeroA = Array.newQuadraticEquationArray(0d, 2d, 4d);
        final Double[] quad = new Double[]{-1d};
        final Double[] quadZeroA = new Double[]{-2d};
        assertEquals(newQuad.length, quad.length, delta);
        assertEquals(newQuadZeroA.length, quadZeroA.length, delta);
        for (int i = 0; i < newQuad.length; i++) {
            assertEquals(newQuad[i], quad[i], delta);
        }
        for (int i = 0; i < newQuadZeroA.length; i++) {
            assertEquals(newQuadZeroA[i], quadZeroA[i], delta);
        }
    }

    @Test
    public void testNewNotDivisibleBy3Array() {
        final Double[] newArr = Array.newNotDivisibleBy3Array(6);
        final Double[] DivArr = new Double[]{1d, 5d, 7d, 11d, 13d, 17d};
        for (int i = 0; i < 6; i++) {
            assertEquals(newArr[i], DivArr[i], delta);
        }
    }

    @Test
    public void testNewAriphmeticProgressionArray() {
        final Double[] ariphArr = Array.newArithmeticProgressionArray(5, 1, 2);
        final Double[] ariphCheck = new Double[]{1d, 3d, 5d, 7d, 9d};
        for (int i = 0; i < 5; i++) {
            assertEquals(ariphArr[i], ariphCheck[i], delta);
        }
    }

    @Test
    public void testMakeOppositeSign() {
        final Double[] arr = new Double[]{-1d, 3d, -5d, 7d, -9d};
        final Array arrType = new Array();
        arrType.makeOppositeSign(arr);
        final Double[] check = new Double[]{1d, -3d, 5d, -7d, 9d};
        for (int i = 0; i < 5; i++) {
            assertEquals(arr[i], check[i]);
        }

    }

    @Test
    public void testNewGeometricProgressionArray() {
        final Double[] ariphArr = Array.newGeometricProgressionArray(5, 1, 2);
        final Double[] ariphCheck = new Double[]{1d, 2d, 4d, 8d, 16d};
        for (int i = 0; i < 5; i++) {
            assertEquals(ariphArr[i], ariphCheck[i], delta);
        }
    }

    @Test
    public void testAllDivisorsOfAnInteger() {
        final Double[] arr = new Double[]{2d, 3d, 13d};
        final Double[] ans = Array.allDivisorsOfAnInteger(78);
        for (int i = 0; i < 3; i++) {
            assertEquals(ans[i], arr[i], delta);
        }
    }

    @Test
    public void testIsInArray() {
        final Double[] arr = new Double[]{2d, 3d, 13d};
        for (int i = 0; i < 3; i++) {
            assertTrue(Array.isInArray(arr[i], arr));
        }
        assertFalse(Array.isInArray(5d, arr));
    }

    @Test
    public void testIsNullInArray() {
        final Integer[] arr = new Integer[]{2, null, 13};
        final Integer[] arr3 = new Integer[]{2, 4, 13};
        assertTrue(Array.isNullInArray(arr));
        assertFalse(Array.isNullInArray(arr3));
    }

    @Test
    public void testHowManyEven() {
        final Integer[] arr = new Integer[]{2, 4, 13};
        assertEquals(Array.howManyEven(arr), 2, delta);
    }

    @Test
    public void testMaxValue() {
        final Integer[] arrInt = new Integer[]{2, 4, 13};
        final Double[] arrDouble = new Double[]{1d, 5d, 2d};

        assertEquals(Array.maxValue(arrInt), 13, delta);
        assertEquals(Array.maxValue(arrDouble), 5d, delta);
        assertNull(Array.maxValue(new Double[]{}));
    }

    @Test
    public void testSumOfEven() {
        final Integer[] arr1 = new Integer[]{2, 4, 13};
        final Integer[] arr2 = new Integer[]{1, 5, 2};

        assertEquals(Array.sumOfEven(arr1), 6, delta);
        assertEquals(Array.sumOfEven(arr2), 2, delta);

    }

    @Test
    public void testSumByEvenIndexes() {
        final Double[] arr1 = new Double[]{2d, 4d, 13d};
        final Double[] arr2 = new Double[]{1d, 5d, 2d};

        assertEquals(Array.sumByEvenIndexes(arr1), 15d, delta);
        assertEquals(Array.sumByEvenIndexes(arr2), 3d, delta);

    }

    @Test
    public void testEachByteInversion() {
        final int[] arr1 = new int[]{2, 4, 13};
        final int[] arr2 = new int[]{1, 5, 2};
        final int[] arr11 = new int[]{~2, ~4, ~13};
        final int[] arr21 = new int[]{~1, ~5, ~2};
        final int[] arr1Ans = Array.eachByteInversion(arr1);
        final int[] arr2Ans = Array.eachByteInversion(arr2);
        final int[] arr11Ans = Array.eachByteInversion(arr1Ans);
        final int[] arr21Ans = Array.eachByteInversion(arr2Ans);
        for (int i = 0; i < 3; i++) {
            assertEquals(arr1Ans[i], arr11[i]);
            assertEquals(arr2Ans[i], arr21[i]);
            assertEquals(arr11Ans[i], arr1[i]);
            assertEquals(arr21Ans[i], arr2[i]);
        }

    }

    @Test
    public void testEachByteInversionByLink() {
        final int[] arr1 = new int[]{2, 4, 13};
        final int[] arr2 = new int[]{1, 5, 2};
        final int[] arr1inv = new int[]{~2, ~4, ~13};
        final int[] arr2inv = new int[]{~1, ~5, ~2};
        final int[] arr11 = new int[]{2, 4, 13};
        final int[] arr21 = new int[]{1, 5, 2};
        Array.eachByteInversionByLink(arr11);
        Array.eachByteInversionByLink(arr21);
        for (int i = 0; i < 3; i++) {
            assertEquals(arr11[i], arr1inv[i]);
            assertEquals(arr21[i], arr2inv[i]);
        }
        Array.eachByteInversionByLink(arr11);
        Array.eachByteInversionByLink(arr21);
        for (int i = 0; i < 3; i++) {
            assertEquals(arr11[i], arr1[i]);
            assertEquals(arr21[i], arr2[i]);
        }
    }

    @Test
    public void testOnePointTwentySix() {
        int[] evenArr = Array.onePointTwentySix(new int[]{1, 2, 3, 4, 5, 6});
        int[] evenAns = new int[]{3, 7, 11};
        int[] oddArr = Array.onePointTwentySix(new int[]{1, 2, 3, 4, 5});

        for (int i = 0; i < 3; i++) {
            assertEquals(evenArr[i], evenAns[i]);
        }
        for (int i = 0; i < 5; i++) {
            assertEquals(oddArr[i], oddArr[i]);
        }
    }

    @Test
    public void testIsEven() {
        boolean[] evenArr = Array.isEven(new int[]{1, 2, 3, 4, 5, 6});
        boolean[] oddArr = Array.isEven(new int[]{1, 10, 3, 24, 5, 2});
        boolean[] ans = new boolean[]{false, true, false, true, false, true};

        for (int i = 0; i < 6; i++) {
            assertEquals(evenArr[i], ans[i]);
            assertEquals(oddArr[i], ans[i]);
        }
    }

    @Test
    public void testSeparate() {
        int[] arr = Array.separate(987654321123456789L);
        long ans = Array.toLong(arr);
        assertEquals(ans,987654321123456789L);
    }


}