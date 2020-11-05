package ru.ssau.tk.lsan.graphicsPack;

public class MadgwickAlgorithm {
    private final double dzta;
    private final double bta;
    private double delta_T;
    private double[] q_est;
    private double[] q_omega;
    private double omega_eps_prev;
    private double[] q_eps_dot;

    public MadgwickAlgorithm() {
        this.bta = 0d;
        this.dzta = 0d;
    }

    public MadgwickAlgorithm(double[] q_est_0, double omega_eps_prev_0, double bta0, double dzta0, double delta_T0) {
        this.bta = bta0;
        this.dzta = dzta0;
        this.delta_T = delta_T0;
        this.q_est = q_est_0;
        this.omega_eps_prev = omega_eps_prev_0;
        this.q_omega = new double[]{0, 0, 0, 0};
    }

    public double[] getQ_est() {
        return q_est;
    }

    protected double dot(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("forbidden length");
        }
        double ans = 0;
        for (int i = 0; i < a.length; i++) {
            ans += a[i] * b[i];
        }
        return ans;
    }

    protected double[] cross(double[] a, double[] b) {
        if (a.length != b.length || a.length != 3) {
            throw new IllegalArgumentException("forbidden length");
        }
        return new double[]{a[1] * b[2] - a[2] * b[1], a[2] * b[0] - a[0] * b[2], a[1] * b[2] - a[2] * b[1]};
    }

    protected double[] quat_mult(double[] a, double[] b) {
        double[] Q = new double[4];
        double[] aCrossB = cross(new double[]{a[1], a[2], a[3]}, new double[]{b[1], b[2], b[3]});
        Q[0] = a[0] * b[0] - dot(new double[]{a[1], a[2], a[3]}, new double[]{b[1], b[2], b[3]});
        Q[1] = a[0] * b[1] + aCrossB[0] + b[0] * a[1];
        Q[2] = a[0] * b[2] + aCrossB[1] + b[0] * a[2];
        Q[3] = a[0] * b[3] + aCrossB[2] + b[0] * a[3];
        return Q;
    }

    protected double[] quat_conj(double[] a) {
        return new double[]{a[0], -a[1], -a[2], -a[3]};
    }

    protected double[][] matrixMultiplication(double[][] a, double[][] b, int l, int m, int n) {
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

    protected double[] matrixMultiplication(double[][] a, double[] bt) {
        //A (l x m) и B (m x n) определяется как Матрица C (l x n)
        if (bt.length != 6) {
            throw new IllegalArgumentException("Length could be 6");
        }
        double[][] b = new double[6][1];
        b[0][0] = bt[0];
        b[1][0] = bt[1];
        b[2][0] = bt[2];
        b[3][0] = bt[3];
        b[4][0] = bt[4];
        b[5][0] = bt[5];
        double[][] c;
        c = matrixMultiplication(a, b, 4, 6, 1);
        return new double[]{c[0][0], c[1][0], c[2][0], c[3][0]};
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
    protected double[] matrixMultiplication(double[] a, double b) {
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
    protected double[] normalizeVector(double[] a) {
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

    protected double[] matrixSum(double[] a, double[] b) {
        double[] c = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i] + b[i];
        }
        return c;
    }

    protected double[] matrixSum(double[] a, double b, int h) {
        double[] c = new double[h];
        for (int i = 0; i < h; i++) {
            c[i] = a[i] + b;
        }
        return c;
    }

    protected double[] matrixDivision(double[] a, double[] b, int h) {
        double[] c = new double[h];
        for (int i = 0; i < h; i++) {
            c[i] = a[i] - b[i];
        }
        return c;
    }

    protected void calculatePosition(double[] a, double[] m, double[] g) {
        double[] f_a = new double[3];
        double[][] J_a = new double[3][4];
        double[] f_m = new double[3];
        double[][] J_m = new double[3][4];
        double[] q_omega_dot = new double[4];

        a = normalizeVector(a);
        m = normalizeVector(m);
        //g = normalizeVector(g);
        g = matrixMultiplication(g,Math.PI/(180*(2<<14)));
        for(int i = 0; i<g.length; i++){
            System.out.print("g"+i+":"+g[i]+" ");
        }
        System.out.print('\n');
        for(int i = 0; i<g.length; i++){
            System.out.print("a"+i+":"+a[i]+" ");
        }
        System.out.print('\n');
        for(int i = 0; i<g.length; i++){
            System.out.print("m"+i+":"+m[i]+" ");
        }
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
        q_eps_dot = normalizeVector(grad_f);

        double[] omega_eps = quat_mult(quat_conj(q_est), q_eps_dot);
        omega_eps = matrixMultiplication(omega_eps, 2 * delta_T);
        omega_eps = matrixSum(omega_eps, omega_eps_prev, 4);
        double[] omega_b = matrixSum(omega_eps, dzta, 4);
        double[] omega_c = matrixDivision(new double[]{0d, g[0], g[1], g[2]}, omega_b, 4);

        q_omega_dot[0] = -0.5 * dot(new double[]{q_est[1], q_est[2], q_est[3]}, new double[]{omega_c[1], omega_c[2], omega_c[3]});
        double[] crossQOmega = cross(new double[]{q_est[1], q_est[2], q_est[3]}, new double[]{omega_c[1], omega_c[2], omega_c[3]});
        q_omega_dot[1] = 0.5 * (q_est[0] * omega_c[1] + crossQOmega[0]);
        q_omega_dot[2] = 0.5 * (q_est[0] * omega_c[2] + crossQOmega[1]);
        q_omega_dot[3] = 0.5 * (q_est[0] * omega_c[3] + crossQOmega[2]);
        q_omega = matrixSum(q_omega, matrixMultiplication(q_omega_dot, delta_T));

        double[] q_est_dot = matrixDivision(q_omega, matrixMultiplication(q_eps_dot, bta), 4);
        q_est = matrixSum(q_est, matrixMultiplication(q_est_dot, delta_T));
        q_est = normalizeVector(q_est);
    }
}
