package ru.ssau.tk.lsan.task2;

public interface ArrayMethods {
    Double[] newEmptyDoubleArray(int size);
    Double[] newFilledDoubleArray(int size);
    Double[] newOddArray(int size);
    Double[] newDescendingEvenArray(int size);
    Double[] newFibonacciArray(int size);
    Double[] newSqrIndexArray(int size);
    Double[] newQuadraticEquationArray(double a, double b, double c);
    Double[] newNotDivisibleBy3Array(int size);
    Double[] newArithmeticProgressionArray(int size, double firstElem, double difference);
    void makeOppositeSign(Double[] arr);
    Double[] newGeometricProgressionArray(int size, double firstElem, double difference);
    Double[] allSimpleNumbersBefore(int n);
    Double[] allDivisorsOfAnInteger(int n);
    boolean isInArray(Double n,Double[] arr);
    boolean isNullInArray(Integer[] arr);
    int howManyEven(Integer[] arr);
    Integer sumOfEven(Integer[] arr);
}
