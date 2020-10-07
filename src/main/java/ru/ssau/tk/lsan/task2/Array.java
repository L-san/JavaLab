package ru.ssau.tk.lsan.task2;

public class Array implements ArrayMethods {
    @Override
    public Double[] newEmptyDoubleArray(int size) {
        return new Double[size];
    }

    @Override
    public Double[] newFilledDoubleArray(int size) {
            Double[] arr = new Double[size];
            arr[0] = 2d;
            arr[size-1] = 2d;
            for (int i = 1; i < size-1; i++) {
                arr[i] = 1d;
            }
            return arr;
    }

    @Override
    public Double[] newOddArray(int size) {
        Double[] arr = new Double[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 2d*i+1d;
        }
        return arr;
    }

    @Override
    public Double[] newDescendingEvenArray(int size) {
        Double[] arr = new Double[size];
        for (int i = size-1; i > -1; i--) {
            arr[i] = 2d*i+2;
        }
        return arr;
    }
}
