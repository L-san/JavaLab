package ru.ssau.tk.lsan.graphicsPack.algorithms;

import org.ejml.simple.SimpleMatrix;

public interface Algorithm {
    SimpleMatrix getQuaternion();
    public void calculatePosition(double[] a, double[] m, double[] g);
}
