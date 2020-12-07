package ru.ssau.tk.lsan.graphicsPack.algorithms;

import ru.ssau.tk.lsan.graphicsPack.operations.KalmanFilter;

public class Madgwick implements Algorithm {
    private final double zeta;
    private final double beta;
    private double dt;
    private double[] q_est;
    private double w_bx, w_by, w_bz;


    public Madgwick(double[] q_est_0, double beta0, double dzta0, double dt) {
        this.beta = beta0;
        this.zeta = dzta0;
        this.dt = dt;
        this.q_est = q_est_0;
    }

    @Override
    public double[] getQuaternion() {
        return this.q_est;
    }

    @Override
    public void calculatePosition(double[] a, double[] m, double[] g) {
        double wx, wy, wz, w_eps_x, w_eps_y, w_eps_z;
        double[] omega_c = new double[4];

        //System.out.println(q_est0+" "+ q_est1+" "+q_est2+" "+q_est3);
        double k = Math.PI / (180 * (2 << 13));
        wx = g[0] * k;
        wy = g[1] * k;
        wz = g[2] * k;
        System.out.println(wx + " " + wy + " " + wz);
        //и только потом уже градиентный спуск!!!
        double[] f_a = new double[]{
                2 * (q_est[1] * q_est[3] - q_est[0] * q_est[2]) - a[0],
                2 * (q_est[0] * q_est[1] + q_est[2] * q_est[3]) - a[1],
                2 * (0.5 - q_est[1] * q_est[1] - q_est[1] * q_est[1]) - a[2]};

        double[][] J_a = new double[][]{
                {-2 * q_est[2], 2 * q_est[3], -2 * q_est[0], 2 * q_est[1]},
                {2 * q_est[1], 2 * q_est[0], 2 * q_est[3], 2 * q_est[2]},
                {0, -4 * q_est[1], -4 * q_est[2], 0}};
        double b_x, b_z;

        double[] f_m = new double[]{
                2 * b_x * (0.5 - q_est[2] * q_est[2] - q_est[3] * q_est[3]) + 2 * b_z * (q_est[1] * q_est[3] - q_est[0] * q_est[2]) - m[0],
                2 * b_x * (q_est[1] * q_est[2] - q_est[0] * q_est[3]) + 2 * b_z * (q_est[1] * q_est[0] + q_est[3] * q_est[2]) - m[1],
                2 * b_x * (q_est[0] * q_est[2] + q_est[1] * q_est[3]) + 2 * b_z * (0.5 - q_est[1] * q_est[1] - q_est[2] * q_est[2]) - m[2]};

        double[][] J_m = new double[][]{
                {-2 * b_z * q_est[2], 2 * b_z * q_est[3], -4 * b_x * q_est[2] - 2 * b_z * q_est[0], -4 * b_x * q_est[3] + 2 * b_z * q_est[1]},
                {-2 * b_x * q_est[3] + 2 * b_z * q_est[1], 2 * b_x * q_est[2] + 2 * b_z * q_est[0], 2 * b_x * q_est[1] + 2 * b_z * q_est[3], -2 * b_x * q_est[0] + 2 * b_z * q_est[2]},
                {2 * b_x * q_est[2], 2 * b_x * q_est[3] - 4 * b_z * q_est[1], 2 * b_x * q_est[0] - 4 * b_z * q_est[2], 2 * b_x * q_est[1]}};

        double[] gradient = new double[]{
                f_a[0] * J_a[0][0] + f_a[1] * J_a[1][0] + f_a[2] * J_a[2][0] + f_m[0] * J_m[0][0] + f_m[1] * J_m[1][0] + f_m[2] * J_m[2][0],
                f_a[0] * J_a[0][1] + f_a[1] * J_a[1][1] + f_a[2] * J_a[2][1] + f_m[0] * J_m[0][1] + f_m[1] * J_m[1][1] + f_m[2] * J_m[2][1],
                f_a[0] * J_a[0][2] + f_a[1] * J_a[1][2] + f_a[2] * J_a[2][2] + f_m[0] * J_m[0][2] + f_m[1] * J_m[1][2] + f_m[2] * J_m[2][2],
                f_a[0] * J_a[0][3] + f_a[1] * J_a[1][3] + f_a[2] * J_a[2][3] + f_m[0] * J_m[0][3] + f_m[1] * J_m[1][3] + f_m[2] * J_m[2][3]};
        double n = Math.sqrt(gradient[0] * gradient[0] + gradient[1] * gradient[1] + gradient[2] * gradient[2] + gradient[3] * gradient[3]);

        double[] q_eps_dot = new double[4];
        q_eps_dot[0] = gradient[0] / n;
        q_eps_dot[1] = gradient[1] / n;
        q_eps_dot[2] = gradient[2] / n;
        q_eps_dot[3] = gradient[3] / n;

        //потом корректировки для омеги

        w_eps_x = 2 * (q_eps_dot[1] * q_est[0] - q_eps_dot[0] * q_est[1] - q_eps_dot[3] * q_est[2] + q_eps_dot[2] * q_est[3]);
        w_eps_y = 2 * (q_eps_dot[2] * q_est[0] + q_eps_dot[3] * q_est[1] - q_eps_dot[0] * q_est[2] - q_eps_dot[1] * q_est[3]);
        w_eps_z = 2 * (q_eps_dot[3] * q_est[0] - q_eps_dot[2] * q_est[1] + q_eps_dot[1] * q_est[2] - q_eps_dot[0] * q_est[3]);

        w_bx += w_eps_x*zeta * dt;
        w_by += w_eps_y*zeta * dt;
        w_bz += w_eps_z*zeta * dt;

        omega_c[0] = wx - w_bx;
        omega_c[0] = wy - w_by;
        omega_c[0] = wz - w_bz;

        //puasson's equations at first
        q_est[0] = q_est[0] - 0.5 * (q_est[1] * wx + q_est[2] * wy + q_est[3] * wz) * dt;
        q_est[1] = q_est[1] + 0.5 * (q_est[0] * wx + q_est[2] * wz - q_est[3] * wy) * dt;
        q_est[2] = q_est[2] + 0.5 * (q_est[0] * wy + q_est[3] * wx - q_est[1] * wz) * dt;
        q_est[3] = q_est[3] + 0.5 * (q_est[0] * wz + q_est[1] * wy - q_est[2] * wx) * dt;


    }
}
