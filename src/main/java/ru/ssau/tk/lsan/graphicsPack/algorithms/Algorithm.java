package ru.ssau.tk.lsan.graphicsPack.algorithms;

import org.ejml.simple.SimpleMatrix;

public interface Algorithm {
    double[] getQuaternion();

    void calculatePosition(double[] a, double[] m, double[] g);
}
