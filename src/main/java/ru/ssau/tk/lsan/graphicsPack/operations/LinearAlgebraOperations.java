package ru.ssau.tk.lsan.graphicsPack.operations;

import org.ejml.simple.SimpleMatrix;

public class LinearAlgebraOperations {

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

    public double[][] matrixMultiplication(double[][] a, double b) {
        //A (l x m) и B (m x n) определяется как Матрица C (l x n)
        double[][] c = new double[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                    c[i][j] = a[i][j] * b;
            }
        }
        return c;
    }

    public double[] matrixMultiplication(double[][] a, double[] bt) {
        //A (l x m) и B (m x n) определяется как Матрица C (l x n)
        double[][] b = new double[bt.length][1];
        for (int i = 0; i < bt.length; i++) {
            b[i][0] = bt[i];
        }
        double[][] c;
        double[] g = new double[a.length];
        c = matrixMultiplication(a, b, a.length, bt.length, 1);
        for (int j = 0; j < a.length; j++) {
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

    public double[][] matrixSum(double[][] a, double[][] b) {
        double[][] c = new double[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                c[i][j] = a[i][j] + b[i][j];
            }
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
//todo
    public double[][] matrixDivision(double[][] a, double[][] b) {
        double[][] c = new double[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                c[i][j] = a[i][j] - b[i][j];
            }
        }
        return c;
    }

    public double[][] transponse(double[][] a) {
        double[][] b = new double[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                b[i][j] = a[j][i];
            }
        }
        return b;
    }

    public double[][] inv(double[][] a) {
        SimpleMatrix am = new SimpleMatrix(a);
        SimpleMatrix cm = am.invert();
        double[][] ans = new double[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                ans[i][j] = cm.get(i, j);
            }
        }
        return ans;
    }
}
