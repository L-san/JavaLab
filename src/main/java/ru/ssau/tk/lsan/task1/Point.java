package ru.ssau.tk.lsan.task1;

import static java.lang.Math.sqrt;

public class Point {
    public final double X;
    public final double Y;
    public final double Z;

    public Point(double X, double Y, double Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public double length() {
        return sqrt(this.X * this.X + this.Y * this.Y + this.Z * this.Z);
    }

    @Override
    public String toString() {
        return new String("["+X+", "+Y+", "+Z+"]");
    }
}
