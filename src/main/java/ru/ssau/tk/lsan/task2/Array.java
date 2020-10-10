package ru.ssau.tk.lsan.task2;

public class Array extends Numbers implements ArrayMethods {
    @Override
    public Double[] newEmptyDoubleArray(int size) {
        return new Double[size];
    }

    @Override
    public Double[] newFilledDoubleArray(int size) {
        Double[] arr = new Double[size];
        arr[0] = 2d;
        arr[size - 1] = 2d;
        for (int i = 1; i < size - 1; i++) {
            arr[i] = 1d;
        }
        return arr;
    }

    @Override
    public Double[] newOddArray(int size) {
        Double[] arr = new Double[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 2d * i + 1d;
        }
        return arr;
    }

    @Override
    public Double[] newDescendingEvenArray(int size) {
        Double[] arr = new Double[size];
        for (int i = size - 1; i > -1; i--) {
            arr[i] = 2d * i + 2;
        }
        return arr;
    }

    @Override
    public Double[] newFibonacciArray(int size) {
        Double[] arr = new Array().newEmptyDoubleArray(size);
        getFibonacci(size, arr);
        return arr;
    }

    @Override
    public Double[] newSqrIndexArray(int size) {
        Double[] arr = new Array().newEmptyDoubleArray(size);
        for (int i = 0; i < size; i++) {
            arr[i] = (double) (i * i);
        }
        return arr;
    }

    @Override
    public Double[] newQuadraticEquationArray(double a, double b, double c) {
        double D = b * b - 4 * a * c;
        Double[] ans = new Double[]{};
        if (a == 0) {
            ans = new Double[]{-c / b};
        } else if (D > 0) {
            ans = new Double[]{(-b - Math.sqrt(D)) / (2 * a), (-b + Math.sqrt(D)) / (2 * a)};
        } else if (D == 0) {
            ans = new Double[]{-b / (2 * a)};
        } else if (D < 0) {
            ans = new Double[]{};
        }
        return ans;
    }

    @Override
    public Double[] newNotDivisibleBy3Array(int size) {

        Double arr[] = new Double[size];
        arr[0] = 1d;
        for (int i = 1; i < size; i++) {
            if (i % 2 == 0) {
                arr[i] = arr[i - 1] + 2d;
            } else {
                arr[i] = arr[i - 1] + 4d;
            }
        }
        return arr;
    }

    @Override
    public Double[] newAriphmeticProgressionArray(int size, double firstElem, double difference) {
        return getAriphmeticProgression(size, firstElem, difference);
    }
}
