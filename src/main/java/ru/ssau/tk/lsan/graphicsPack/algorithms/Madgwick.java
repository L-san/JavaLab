package ru.ssau.tk.lsan.graphicsPack.algorithms;

public class Madgwick implements Algorithm {
    private final double zeta;
    private final double beta;
    private final double dt;
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
        double[] w_c = new double[4];

        //System.out.println(q_est0+" "+ q_est1+" "+q_est2+" "+q_est3);
        //gyro measurements
        // double k = Math.PI / (180 * (2 << 13));
        double k = Math.PI / (180 * 7);//70 mdps/LSB
        wx = g[0] * k;
        wy = g[1] * k;
        wz = g[2] * k;
        System.out.println(wx + " " + wy + " " + wz);

        double nm = Math.sqrt(m[0] * m[0] + m[1] * m[1] + m[2] * m[2]);
        if (nm == 0) {
            nm = 1;
        }
        m[0] /= nm;
        m[1] /= nm;
        m[2] /= nm;

        double na = Math.sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
        if (na == 0) {
            na = 1;
        }
        a[0] /= na;
        a[1] /= na;
        a[2] /= na;

        //и только потом уже градиентный спуск!!!
        //gradient descent algorithm
        double[] f_a = new double[]{
                2 * (q_est[1] * q_est[3] - q_est[0] * q_est[2]) - a[0],
                2 * (q_est[0] * q_est[1] + q_est[2] * q_est[3]) - a[1],
                2 * (0.5 - q_est[1] * q_est[1] - q_est[1] * q_est[1]) - a[2]};

        double[][] J_a = new double[][]{
                {-2 * q_est[2], 2 * q_est[3], -2 * q_est[0], 2 * q_est[1]},
                {2 * q_est[1], 2 * q_est[0], 2 * q_est[3], 2 * q_est[2]},
                {0, -4 * q_est[1], -4 * q_est[2], 0}};

        //direction of the magnetic field
        double h_x, h_y, h_z;
        h_x = 2 * (m[2] * q_est[0] * q_est[2] + m[1] * q_est[1] * q_est[2] - m[1] * q_est[0] * q_est[3] + m[2] * q_est[1] * q_est[3]) + m[0] * (q_est[0] * q_est[0] + q_est[1] * q_est[1] - q_est[2] * q_est[2] - q_est[3] * q_est[3]);
        h_y = 2 * (-m[2] * q_est[0] * q_est[1] + m[0] * q_est[1] * q_est[2] + m[0] * q_est[0] * q_est[3] + m[2] * q_est[2] * q_est[3]) + m[1] * (q_est[0] * q_est[0] - q_est[1] * q_est[1] + q_est[2] * q_est[2] - q_est[3] * q_est[3]);
        h_z = 2 * (m[1] * q_est[0] * q_est[1] - m[0] * q_est[0] * q_est[2] + m[0] * q_est[1] * q_est[3] + m[1] * q_est[2] * q_est[3]) + m[2] * (q_est[0] * q_est[0] - q_est[1] * q_est[1] - q_est[2] * q_est[2] + q_est[3] * q_est[3]);
        //reference direction of the magnetic field
        double b_x, b_z;
        b_x = Math.sqrt(h_x * h_x + h_y * h_y);
        b_z = h_z;

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
        if (n == 0) {
            n = 1;
        }
        double[] q_eps_dot = new double[4];
        q_eps_dot[0] /= n;
        q_eps_dot[1] /= n;
        q_eps_dot[2] /= n;
        q_eps_dot[3] /= n;

        //compensate gyro drift
        w_eps_x = 2 * (q_eps_dot[1] * q_est[0] - q_eps_dot[0] * q_est[1] - q_eps_dot[3] * q_est[2] + q_eps_dot[2] * q_est[3]);
        w_eps_y = 2 * (q_eps_dot[2] * q_est[0] + q_eps_dot[3] * q_est[1] - q_eps_dot[0] * q_est[2] - q_eps_dot[1] * q_est[3]);
        w_eps_z = 2 * (q_eps_dot[3] * q_est[0] - q_eps_dot[2] * q_est[1] + q_eps_dot[1] * q_est[2] - q_eps_dot[0] * q_est[3]);

        w_bx += w_eps_x * zeta * dt;
        w_by += w_eps_y * zeta * dt;
        w_bz += w_eps_z * zeta * dt;

        w_c[1] = wx - w_bx;
        w_c[2] = wy - w_by;
        w_c[3] = wz - w_bz;

        //use compensated w in poisson's equations!!!
        wx = w_c[1];
        wy = w_c[2];
        wz = w_c[3];
        double[] q_omega_dot = new double[4];
        q_omega_dot[0] = -0.5 * (q_est[1] * wx + q_est[2] * wy + q_est[3] * wz);
        q_omega_dot[1] = 0.5 * (q_est[0] * wx + q_est[2] * wz - q_est[3] * wy);
        q_omega_dot[2] = 0.5 * (q_est[0] * wy + q_est[3] * wx - q_est[1] * wz);
        q_omega_dot[3] = 0.5 * (q_est[0] * wz + q_est[1] * wy - q_est[2] * wx);

        double[] q_est_dot = new double[4];
        q_est_dot[0] = q_omega_dot[0] - beta * q_eps_dot[0];
        q_est_dot[1] = q_omega_dot[1] - beta * q_eps_dot[1];
        q_est_dot[2] = q_omega_dot[2] - beta * q_eps_dot[2];
        q_est_dot[3] = q_omega_dot[3] - beta * q_eps_dot[3];


        //poisson's equations
        q_est[0] = q_est[0] + q_est_dot[0] * dt;
        q_est[1] = q_est[1] + q_est_dot[1] * dt;
        q_est[2] = q_est[2] + q_est_dot[2] * dt;
        q_est[3] = q_est[3] + q_est_dot[3] * dt;

        double nq = Math.sqrt(q_est[0] * q_est[0] + q_est[1] * q_est[1] + q_est[2] * q_est[2] + q_est[3] * q_est[3]);
        if (nq == 0) {
            nq = 1;
        }
        q_est[0] /= nq;
        q_est[1] /= nq;
        q_est[2] /= nq;
        q_est[3] /= nq;
    }
}
