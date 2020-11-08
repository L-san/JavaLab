package ru.ssau.tk.lsan.graphicsPack.algorithms;

import org.ejml.simple.SimpleMatrix;

public class MadgwickAlgorithm implements Algorithm {
    private final double zeta;
    private final double beta;
    private double delta_T;
    private double[] q_est;
    private double[] q_omega;
    private double[] omega_eps_prev;
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
    }

    @Override
    public SimpleMatrix getQuaternion() {
        return new SimpleMatrix(4, 1, true, q_est);
    }

    public double dot(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("forbidden length");
        }
        double ans = 0;
        for (int i = 0; i < a.length; i++) {
            ans += a[i] * b[i];
        }
        return ans;
    }

    public double[] cross(double[] a, double[] b) {
        if (a.length != b.length || a.length != 3) {
            throw new IllegalArgumentException("forbidden length");
        }
        return new double[]{a[1] * b[2] - a[2] * b[1], a[2] * b[0] - a[0] * b[2], a[1] * b[2] - a[2] * b[1]};
    }

    public double[] quat_mult(double[] a, double[] b) {
        double[] Q = new double[4];
        double[] aCrossB = cross(new double[]{a[1], a[2], a[3]}, new double[]{b[1], b[2], b[3]});
        Q[0] = a[0] * b[0] - dot(new double[]{a[1], a[2], a[3]}, new double[]{b[1], b[2], b[3]});
        Q[1] = a[0] * b[1] + aCrossB[0] + b[0] * a[1];
        Q[2] = a[0] * b[2] + aCrossB[1] + b[0] * a[2];
        Q[3] = a[0] * b[3] + aCrossB[2] + b[0] * a[3];
        return Q;
    }

    public double[] quat_conj(double[] a) {
        return new double[]{a[0], -a[1], -a[2], -a[3]};
    }

    public double[][] matrixMultiplication(double[][] a, double[][] b, int l, int m, int n) {
        //A (l x m) и B (m x n) определяется как Матрица C (l x n)
        double[][] c = new double[l][n];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < m; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return c;
    }

    public double[] matrixMultiplication(double[][] a, double[] bt) {
        //A (l x m) и B (m x n) определяется как Матрица C (l x n)
        double[][] b = new double[bt.length][1];
        for(int i = 0; i<bt.length;i++){
            b[i][0] = bt[i];
        }
        double[][] c;
        double[] g = new double[a.length];
        c = matrixMultiplication(a, b, a.length, bt.length, 1);
        for(int j = 0; j<a.length; j++){
            g[j] = c[j][0];
        }
        return g;
    }

    /*protected double[][] matrixMultiplication(double[][] a, double b, int l, int m) {
        double[][] c = new double[l][m];
        for (int i = 0; i < l; ++i) {
            for (int j = 0; j < m; ++j) {
                c[i][j] = a[i][j] * b;
            }
        }
        return c;
    }
    */
    public double[] matrixMultiplication(double[] a, double b) {
        double[] c = new double[a.length];
        for (int i = 0; i < a.length; ++i) {
            c[i] = a[i] * b;
        }
        return c;
    }

    /*  protected double[][] matrixSum(double[][] a, double[][] b, int h, int w) {
          double[][] c = new double[][]{};
          for (int i = 0; i < h; i++) {
              for (int j = 0; j < w; j++) {
                  c[i][j] = a[i][j] + b[i][j];
              }
          }
          return c;
      }
  */
    public double[] normalizeVector(double[] a) {
        double[] c = new double[a.length];
        double norm = Math.sqrt(dot(a, a));
        if (norm != 0) {
            for (int i = 0; i < a.length; i++) {
                c[i] = a[i] / norm;
            }
        } else {
            for (int i = 0; i < a.length; i++) {
                c[i] = 0;
            }
        }
        return c;
    }

    public double[] matrixSum(double[] a, double[] b) {
        double[] c = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i] + b[i];
        }
        return c;
    }

    public double[] matrixSum(double[] a, double b, int h) {
        double[] c = new double[h];
        for (int i = 0; i < h; i++) {
            c[i] = a[i] + b;
        }
        return c;
    }

    public double[] matrixDivision(double[] a, double[] b, int h) {
        double[] c = new double[h];
        for (int i = 0; i < h; i++) {
            c[i] = a[i] - b[i];
        }
        return c;
    }

    @Override
    public void calculatePosition(double[] a, double[] m, double[] g) {
        double[] f_a = new double[3];
        double[][] J_a = new double[3][4];
        double[] f_m = new double[3];
        double[][] J_m = new double[3][4];
        double[] q_omega_dot = new double[4];

        a = normalizeVector(a);
        m = matrixMultiplication(m,1e-5/(2 << 14));
        m = matrixMultiplication(magnetometerDataCorrectionMatrix,matrixDivision(m,magnetometerShift,3));
        m = normalizeVector(m);
        //g = normalizeVector(g);
        g = matrixMultiplication(g, Math.PI / (180 * (2 << 14)));
        System.out.print('\n');
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
        //q_omega_dot[1] = 0.5 * (q_est[0] * omega_c[1] + crossQOmega[0]);
        //q_omega_dot[2] = 0.5 * (q_est[0] * omega_c[2] + crossQOmega[1]);
        //q_omega_dot[3] = 0.5 * (q_est[0] * omega_c[3] + crossQOmega[2]);
        q_omega_dot[1] = 0.5 * (q_est[0] * g[0] + crossQOmega[0]);
        q_omega_dot[2] = 0.5 * (q_est[0] * g[1] + crossQOmega[1]);
        q_omega_dot[3] = 0.5 * (q_est[0] * g[2] + crossQOmega[2]);
        q_omega = matrixSum(q_omega, matrixMultiplication(q_omega_dot, delta_T));

        double[] q_est_dot = matrixDivision(q_omega, matrixMultiplication(q_eps_dot, beta), 4);
        q_est = matrixSum(q_est, matrixMultiplication(q_est_dot, delta_T));
        q_est = normalizeVector(q_est);
    }

    /*@Override
    public void calculatePosition(double[] a, double[] m, double[] g){
        double q1 = q_est[0], q2 = q_est[1], q3 = q_est[2], q4 = q_est[3];
        double norm;
        double ax = a[0];double ay = a[1];double az = a[2];
        double mx = m[0];double my = m[1];double mz = m[2];
        double k = Math.PI / (180 * (2 << 14));
        double gx = g[0]*k;double gy = g[1]*k+2e-4;double gz = g[2]*k;

        double hx, hy, bx2, bz2;
        double s1, s2, s3, s4;
        double qDot1, qDot2, qDot3, qDot4;

        double q1mx2;
        double q1my2;
        double q1mz2;
        double q2mx2;
        double bx4;
        double bz4;
        double q12 = 2f * q1;
        double q22 = 2f * q2;
        double q32 = 2f * q3;
        double q42 = 2f * q4;
        double q1q32 = 2f * q1 * q3;
        double q3q42 = 2f * q3 * q4;
        double q1q1 = q1 * q1;
        double q1q2 = q1 * q2;
        double q1q3 = q1 * q3;
        double q1q4 = q1 * q4;
        double qq22 = q2 * q2;
        double qq32 = q2 * q3;
        double qq42 = q2 * q4;
        double q3q3 = q3 * q3;
        double q3q4 = q3 * q4;
        double q4q4 = q4 * q4;

        norm = (double)Math.sqrt(ax * ax + ay * ay + az * az);
        if (norm == 0f) return;
        norm = 1 / norm;
        ax *= norm;
        ay *= norm;
        az *= norm;

        norm = (double)Math.sqrt(mx * mx + my * my + mz * mz);
        if (norm == 0f) return;
        norm = 1 / norm;
        mx *= norm;
        my *= norm;
        mz *= norm;

        q1mx2 = 2f * q1 * mx;
        q1my2 = 2f * q1 * my;
        q1mz2 = 2f * q1 * mz;
        q2mx2 = 2f * q2 * mx;
        hx = mx * q1q1 - q1my2 * q4 + q1mz2 * q3 + mx * qq22 + q22 * my * q3 + q22 * mz * q4 - mx * q3q3 - mx * q4q4;
        hy = q1mx2 * q4 + my * q1q1 - q1mz2 * q2 + q2mx2 * q3 - my * qq22 + my * q3q3 + q32 * mz * q4 - my * q4q4;
        bx2 = (double)Math.sqrt(hx * hx + hy * hy);
        bz2 = -q1mx2 * q3 + q1my2 * q2 + mz * q1q1 + q2mx2 * q4 - mz * qq22 + q32 * my * q4 - mz * q3q3 + mz * q4q4;
        bx4 = 2f * bx2;
        bz4 = 2f * bz2;

        s1 = -q32 * (2f * qq42 - q1q32 - ax) + q22 * (2f * q1q2 + q3q42 - ay) - bz2 * q3 * (bx2 * (0.5f - q3q3 - q4q4) + bz2 * (qq42 - q1q3) - mx) + (-bx2 * q4 + bz2 * q2) * (bx2 * (qq32 - q1q4) + bz2 * (q1q2 + q3q4) - my) + bx2 * q3 * (bx2 * (q1q3 + qq42) + bz2 * (0.5f - qq22 - q3q3) - mz);
        s2 = q42 * (2f * qq42 - q1q32 - ax) + q12 * (2f * q1q2 + q3q42 - ay) - 4f * q2 * (1 - 2f * qq22 - 2f * q3q3 - az) + bz2 * q4 * (bx2 * (0.5f - q3q3 - q4q4) + bz2 * (qq42 - q1q3) - mx) + (bx2 * q3 + bz2 * q1) * (bx2 * (qq32 - q1q4) + bz2 * (q1q2 + q3q4) - my) + (bx2 * q4 - bz4 * q2) * (bx2 * (q1q3 + qq42) + bz2 * (0.5f - qq22 - q3q3) - mz);
        s3 = -q12 * (2f * qq42 - q1q32 - ax) + q42 * (2f * q1q2 + q3q42 - ay) - 4f * q3 * (1 - 2f * qq22 - 2f * q3q3 - az) + (-bx4 * q3 - bz2 * q1) * (bx2 * (0.5f - q3q3 - q4q4) + bz2 * (qq42 - q1q3) - mx) + (bx2 * q2 + bz2 * q4) * (bx2 * (qq32 - q1q4) + bz2 * (q1q2 + q3q4) - my) + (bx2 * q1 - bz4 * q3) * (bx2 * (q1q3 + qq42) + bz2 * (0.5f - qq22 - q3q3) - mz);
        s4 = q22 * (2f * qq42 - q1q32 - ax) + q32 * (2f * q1q2 + q3q42 - ay) + (-bx4 * q4 + bz2 * q2) * (bx2 * (0.5f - q3q3 - q4q4) + bz2 * (qq42 - q1q3) - mx) + (-bx2 * q1 + bz2 * q3) * (bx2 * (qq32 - q1q4) + bz2 * (q1q2 + q3q4) - my) + bx2 * q2 * (bx2 * (q1q3 + qq42) + bz2 * (0.5f - qq22 - q3q3) - mz);
        norm = 1f / (double)Math.sqrt(s1 * s1 + s2 * s2 + s3 * s3 + s4 * s4);
        s1 *= norm;
        s2 *= norm;
        s3 *= norm;
        s4 *= norm;

        qDot1 = 0.5f * (-q2 * gx - q3 * gy - q4 * gz) - bta * s1;
        qDot2 = 0.5f * (q1 * gx + q3 * gz - q4 * gy) - bta * s2;
        qDot3 = 0.5f * (q1 * gy - q2 * gz + q4 * gx) - bta * s3;
        qDot4 = 0.5f * (q1 * gz + q2 * gy - q3 * gx) - bta * s4;

        q1 += qDot1 * delta_T;
        q2 += qDot2 * delta_T;
        q3 += qDot3 * delta_T;
        q4 += qDot4 * delta_T;
        norm = 1f / (double)Math.sqrt(q1 * q1 + q2 * q2 + q3 * q3 + q4 * q4);
        q_est[0] = q1 * norm;
        q_est[1] = q2 * norm;
        q_est[2] = q3 * norm;
        q_est[3] = q4 * norm;}*/
}
