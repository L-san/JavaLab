package ru.ssau.tk.lsan.graphicsPack.operations;

import ru.ssau.tk.lsan.graphicsPack.operations.LinearAlgebraOperations;

public class KalmanFilter extends LinearAlgebraOperations {
    private double[] x;
    private double[][] P, F, H, Q, R;
    private final int nx;

    public KalmanFilter(double[] x0,double Q0,double R0) {
        this.x = x0;
        this.nx = x0.length;
        double[][] i = new double[][]{{1d,0d,0d},{0d,1d,0d},{0d,0d,1d}};
        this.F = i;
        this.H = i;
        this.P = i;
        this.Q = matrixMultiplication(i,Q0);
        this.R = matrixMultiplication(i,R0);
    }

    public double[] getX() {
        return x;
    }

    public void doFiltering(double[] z) {
        //predict
        x = matrixMultiplication(F, x);
        P = matrixSum(matrixMultiplication(matrixMultiplication(F, P, nx, nx, nx), transponse(F), nx, nx, nx), Q);
        //update
        double[][] S;
        S = matrixSum(matrixMultiplication(matrixMultiplication(H, P, nx, nx, nx), transponse(H), nx, nx, nx), R);
        double[][] K;
        K = matrixMultiplication(matrixMultiplication(P, transponse(H), nx, nx, nx), transponse(inv(S)), nx, nx, nx);
        x = matrixSum(x, matrixMultiplication(K, matrixDivision(z, matrixMultiplication(H, x), nx)));
        P = matrixDivision(P, matrixMultiplication(matrixMultiplication(P, K, nx, nx, nx), H, nx, nx, nx));
    }

}
