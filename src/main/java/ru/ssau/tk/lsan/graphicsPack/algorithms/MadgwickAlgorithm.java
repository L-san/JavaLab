package ru.ssau.tk.lsan.graphicsPack.algorithms;

import ru.ssau.tk.lsan.graphicsPack.operations.KalmanFilter;
import ru.ssau.tk.lsan.graphicsPack.operations.LinearAlgebraOperations;

public class MadgwickAlgorithm extends LinearAlgebraOperations implements Algorithm {
    private final double zeta;
    private final double beta;
    private double delta_T;
    private double[] q_est;
    private double[] q_omega;
    private double[] omega_eps_prev;
    private KalmanFilter filter;
    private final double[][] magnetometerDataCorrectionMatrix = new double[][]{{0.965132504064866, 0.0148210866477187, 0.0214381657657772},
            {0.0148210866477187, 1.05960526844393, 0.121793933417382},
            {0.0214381657657772, 0.121793933417382, 0.992455611730103}};
    private final double[] magnetometerShift = new double[]{2.05162344168240e-07,	4.97814252920449e-07,	-3.40182259511065e-07};

    public MadgwickAlgorithm() {
        this.beta = 0d;
        this.zeta = 0d;
    }

    public MadgwickAlgorithm(double[] q_est_0, double[] omega_eps_prev_0, double beta0, double dzta0, double delta_T0) {
        this.beta = beta0;
        this.zeta = dzta0;
        this.delta_T = delta_T0;
        this.q_est = q_est_0;
        this.omega_eps_prev = omega_eps_prev_0;
        this.q_omega = new double[]{0, 0, 0, 0};
        this.filter = new KalmanFilter(new double[]{0.1811e-04,-0.7350e-04,0.1598e-04},0d,50d);
    }

    @Override
    public double[] getQuaternion() {
        return q_est;
    }

    @Override
    public void calculatePosition(double[] a, double[] m, double[] g) {
        double[] f_a = new double[3];
        double[][] J_a = new double[3][4];
        double[] f_m = new double[3];
        double[][] J_m = new double[3][4];
        double[] q_omega_dot = new double[4];

        a = normalizeVector(a);
       // m = matrixMultiplication(m,1e-5/(2 << 14));
        //m = matrixMultiplication(magnetometerDataCorrectionMatrix,matrixDivision(m,magnetometerShift,3));
        m = normalizeVector(m);
        //g = normalizeVector(g);
        g = matrixMultiplication(g, Math.PI / (180 * (2 << 13)));
        //filter.doFiltering(g);
        //filter.getX();
        System.out.println("g0: "+g[0]+" g1: "+g[1]+" g2:"+g[2]*180/Math.PI);
        f_a[0] = 2 * (q_est[1] * q_est[3] - q_est[0] * q_est[2]) - a[0];
        f_a[1] = 2 * (q_est[0] * q_est[1] + q_est[2] * q_est[3]) - a[1];
        f_a[2] = 2 * (0.5 - q_est[1] * q_est[1] - q_est[2] * q_est[2]) - a[2];

        J_a[0][0] = -2 * q_est[2];
        J_a[0][1] = 2 * q_est[3];
        J_a[0][2] = -2 * q_est[0];
        J_a[0][3] = 2 * q_est[1];
        J_a[1][0] = 2 * q_est[1];
        J_a[1][1] = 2 * q_est[0];
        J_a[1][2] = 2 * q_est[3];
        J_a[1][3] = 2 * q_est[2];
        J_a[2][0] = 0;
        J_a[2][1] = -4 * q_est[1];
        J_a[2][2] = -4 * q_est[2];
        J_a[2][3] = 0;

        double[] h = quat_mult(q_est, new double[]{0d, m[0], m[1], m[2]});
        h = quat_mult(h, quat_conj(q_est));
        double[] b = new double[]{0, Math.sqrt(h[1] * h[1] + h[2] * h[2]), 0, h[3]};

        f_m[0] = 2 * b[1] * (0.5 - q_est[2] * q_est[2] - q_est[3] * q_est[3]) + 2 * b[3] * (q_est[1] * q_est[3] - q_est[0] * q_est[2]) - m[0];
        f_m[1] = 2 * b[1] * (q_est[1] * q_est[2] - q_est[0] * q_est[3]) + 2 * b[3] * (q_est[1] * q_est[0] + q_est[3] * q_est[2]) - m[1];
        f_m[2] = 2 * b[1] * (q_est[0] * q_est[2] + q_est[1] * q_est[3]) + 2 * b[3] * (0.5 - q_est[1] * q_est[1] - q_est[2] * q_est[2]) - m[2];

        J_m[0][0] = -2 * b[3] * q_est[2];
        J_m[0][1] = 2 * b[3] * q_est[3];
        J_m[0][2] = -4 * b[1] * q_est[2] - 2 * b[3] * q_est[0];
        J_m[0][3] = -4 * b[1] * q_est[3] + 2 * b[3] * q_est[1];
        J_m[1][0] = -2 * b[1] * q_est[3] + 2 * b[3] * q_est[1];
        J_m[1][1] = 2 * b[1] * q_est[2] + 2 * b[3] * q_est[0];
        J_m[1][2] = 2 * b[1] * q_est[1] + 2 * b[3] * q_est[3];
        J_m[1][3] = -2 * b[1] * q_est[0] + 2 * b[3] * q_est[2];
        J_m[2][0] = 2 * b[1] * q_est[2];
        J_m[2][1] = 2 * b[1] * q_est[3] - 4 * b[3] * q_est[1];
        J_m[2][2] = 2 * b[1] * q_est[0] - 4 * b[3] * q_est[2];
        J_m[2][3] = 2 * b[1] * q_est[1];

        double[][] J = new double[][]{
                {J_a[0][0], J_a[1][0], J_a[2][0], J_m[0][0], J_m[1][0], J_m[2][0]},
                {J_a[0][1], J_a[1][1], J_a[2][1], J_m[0][1], J_m[1][1], J_m[2][1]},
                {J_a[0][2], J_a[1][2], J_a[2][2], J_m[0][2], J_m[1][2], J_m[2][2],},
                {J_a[0][3], J_a[1][3], J_a[2][3], J_m[0][3], J_m[1][3], J_m[2][3]}};

        double[] f = new double[]{f_a[0], f_a[1], f_a[2], f_m[0], f_m[1], f_m[2]};
        double[] grad_f = matrixMultiplication(J, f);
        double[] q_eps_dot = normalizeVector(grad_f);

        double[] omega_eps = matrixMultiplication(quat_mult(quat_conj(q_est), q_eps_dot), 2);
        omega_eps_prev = matrixSum(omega_eps_prev,matrixMultiplication(matrixMultiplication(omega_eps,delta_T),zeta));
        double[] omega_c = matrixDivision(new double[]{0d, g[0], g[1], g[2]}, omega_eps_prev, 4);

        q_omega_dot[0] = -0.5 * dot(new double[]{q_est[1], q_est[2], q_est[3]}, new double[]{omega_c[1], omega_c[2], omega_c[3]});
        double[] crossQOmega = cross(new double[]{q_est[1], q_est[2], q_est[3]}, new double[]{omega_c[1], omega_c[2], omega_c[3]});
        q_omega_dot[1] = 0.5 * (q_est[0] * omega_c[1] + crossQOmega[0]);
        q_omega_dot[2] = 0.5 * (q_est[0] * omega_c[2] + crossQOmega[1]);
        q_omega_dot[3] = 0.5 * (q_est[0] * omega_c[3] + crossQOmega[2]);
        q_omega = matrixSum(q_omega, matrixMultiplication(q_omega_dot, delta_T));

        double[] q_est_dot = matrixDivision(q_omega_dot, matrixMultiplication(q_eps_dot, beta), 4);
        double[] q_est_prev = q_est;
        q_est = matrixSum(q_est, matrixMultiplication(q_est_dot, delta_T));
        //q_est = matrixMultiplication(q_est_dot, delta_T);
        q_est = quat_mult(quat_conj(q_est_prev),q_est);
        q_est = normalizeVector(q_est);
        System.out.println("q0: "+q_est[0]+" q1: "+q_est[1]+" q2: "+q_est[2]+" q3: "+q_est[3]);
    }
}
