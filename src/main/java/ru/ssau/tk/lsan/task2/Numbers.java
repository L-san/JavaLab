package ru.ssau.tk.lsan.task2;

public class Numbers {
    public Double getFibonacci(int n, Double[] MemoryArr) {
        if (n == MemoryArr.length) {
            n -= 1;
        }
        if (n > MemoryArr.length) {
            throw new IllegalArgumentException("Array Index is Out of Bounds");
        }
        if (MemoryArr[n] != null) {
            return MemoryArr[n];
        } else {
            return (n < 2d ? (MemoryArr[n] = 1d) : (MemoryArr[n] = getFibonacci(n - 1, MemoryArr) + getFibonacci(n - 2, MemoryArr)));
        }
    }

    public Double[] getAriphmeticProgression(int size, double a0, double d) {
        Double[] MemoryArr = new Double[size];
        for (int i = 0; i < size; i++) {
            MemoryArr[i] = a0 + i * d;
        }
        return MemoryArr;
    }

    public Double[] getGeometricProgression(int size, double a0, double d) {
        Double[] MemoryArr = new Double[size];
        MemoryArr[0] = a0;
        for (int i = 1; i < size; i++) {
            MemoryArr[i] = MemoryArr[i-1]*d;
        }
        return MemoryArr;
    }

}
