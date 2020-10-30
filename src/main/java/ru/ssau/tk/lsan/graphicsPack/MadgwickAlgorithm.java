package ru.ssau.tk.lsan.graphicsPack;

public class MadgwickAlgorithm {
    private double q_init;

    MadgwickAlgorithm() {

    }

    protected double dot(double[] a, double[] b) {
        if (a.length != b.length || a.length != 3) {
            throw new IllegalArgumentException("forbidden length");
        }
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }

    protected double[] cross(double[] a, double[] b) {
        if (a.length != b.length || a.length != 3) {
            throw new IllegalArgumentException("forbidden length");
        }
        return new double[]{a[1] * b[2] - a[2] * b[1], a[2] * b[0] - a[0] * b[2], a[1] * b[2] - a[2] * b[1]};
    }

    protected double[] quat_mult(double[] a, double[] b) {
        return new double[]{0,0,0};
    }

    protected double calculatePosition(double[] q_est, double[] a) {
        double[] f_a = new double[3];
        double[][] J_a = new double[3][4];

        f_a[0] = 2 * (q_est[1] * q_est[3] - q_est[0] * q_est[2]) - a[0];
        f_a[1] = 2 * (q_est[0] * q_est[1] + q_est[2] * q_est[3]) - a[1];
        f_a[2] = 2 * (0.5 - q_est[1] * q_est[1] - q_est[2] * q_est[2]) - a[2];

        J_a[0][1] = -2 * q_est[2];
        J_a[0][2] = 2 * q_est[3];
        J_a[0][3] = -2 * q_est[0];
        J_a[0][4] = 2 * q_est[1];
        J_a[1][1] = 2 * q_est[1];
        J_a[1][2] = 2 * q_est[0];
        J_a[1][3] = 2 * q_est[3];
        J_a[1][4] = 2 * q_est[2];
        J_a[2][1] = 0;
        J_a[2][2] = -4 * q_est[1];
        J_a[2][3] = -4 * q_est[2];
        J_a[2][4] = 0;

        return 0d;
    }
}
