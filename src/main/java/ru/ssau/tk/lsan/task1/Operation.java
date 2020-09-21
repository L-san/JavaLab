package ru.ssau.tk.lsan.task1;

public abstract class Operation {
    abstract double apply(double number);

    public double applyTriple(double number) {
        return apply(apply(apply(number)));
    }
}
