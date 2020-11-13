package ru.ssau.tk.lsan.task1;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class PointsTest extends Points{
    public static double delta = 0.001;

    final Point zeroPoint = new Point(0,0,0);
    final Point onePoint = new Point(1,1,1);
    final Point oneTwoThreePoint = new Point(1,2,3);

    private boolean equalsApproximately(double a, double b) {
        return (Math.abs(a - b) < delta);
    }
    private boolean equalsApproximately(Point a, Point b) {
        return ((Math.abs(a.X - b.X) < delta) & (Math.abs(a.Y - b.Y) < delta) & (Math.abs(a.Z - b.Z) < delta));
    }

    @Test
    public void testOpposite() {
        Point testPoint = new Point(1, 1, 1);
        testPoint = Points.opposite(testPoint);
        assertEquals(testPoint.X, -1);
        assertEquals(testPoint.Y, -1);
        assertEquals(testPoint.Z, -1);
    }

    @Test
    public void testSum() {
        assertTrue(equalsApproximately(sum(zeroPoint,onePoint),onePoint));
        assertTrue(equalsApproximately(sum(zeroPoint,oneTwoThreePoint),oneTwoThreePoint));
        assertTrue(equalsApproximately(sum(oneTwoThreePoint,onePoint),new Point(2,3,4)));
    }

    @Test
    public void testSubstract() {
        assertTrue(equalsApproximately(substract(onePoint,zeroPoint),onePoint));
        assertTrue(equalsApproximately(substract(oneTwoThreePoint,zeroPoint),oneTwoThreePoint));
        assertTrue(equalsApproximately(substract(oneTwoThreePoint,onePoint),new Point(0,1,2)));
    }

    @Test
    public void testMultiply() {
        assertTrue(equalsApproximately(multiply(onePoint,zeroPoint),zeroPoint));
        assertTrue(equalsApproximately(multiply(oneTwoThreePoint,zeroPoint),zeroPoint));
        assertTrue(equalsApproximately(multiply(oneTwoThreePoint,onePoint),oneTwoThreePoint));
    }

    @Test
    public void testDivide() {
        assertTrue(equalsApproximately(divide(onePoint,oneTwoThreePoint),new Point( 1d,1d/2,1d/3)));
        assertTrue(equalsApproximately(divide(zeroPoint,oneTwoThreePoint),zeroPoint));
        assertTrue(equalsApproximately(divide(oneTwoThreePoint,onePoint),oneTwoThreePoint));
    }

    @Test
    public void testEnlarge() {
        assertTrue(equalsApproximately(enlarge(onePoint,2),new Point( 2,2,2)));
        assertTrue(equalsApproximately(enlarge(zeroPoint,2),zeroPoint));
        assertTrue(equalsApproximately(enlarge(oneTwoThreePoint,2),new Point( 2,4,6)));
    }

    @Test
    public void testLength() {
        assertTrue(equalsApproximately(onePoint.length(),Math.sqrt(3)));
        assertTrue(equalsApproximately(zeroPoint.length(),0));
        assertTrue(equalsApproximately(oneTwoThreePoint.length(),Math.sqrt(14)));
    }

    @Test
    public void testInverse() {
        assertTrue(equalsApproximately(inverse(oneTwoThreePoint),new Point( 1d,1d/2,1d/3)));
        assertTrue(equalsApproximately(inverse(onePoint),onePoint));
    }

    @Test
    public void testVectorProduct() {
        assertTrue(equalsApproximately(vectorProduct(onePoint,zeroPoint),zeroPoint));
        assertTrue(equalsApproximately(vectorProduct(onePoint,oneTwoThreePoint),new Point(1,-2,1)));
        assertTrue(equalsApproximately(vectorProduct(zeroPoint,oneTwoThreePoint),zeroPoint));
    }

    @Test
    public void testScalarProduct() {
        assertTrue(equalsApproximately(scalarProduct(onePoint,zeroPoint),0));
        assertTrue(equalsApproximately(scalarProduct(onePoint,oneTwoThreePoint),6));
        assertTrue(equalsApproximately(scalarProduct(zeroPoint,oneTwoThreePoint),0));
    }
}