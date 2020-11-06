package ru.ssau.tk.lsan.graphicsPack.algorithms;

import org.ejml.simple.SimpleMatrix;
import org.ejml.simple.SimpleSVD;

public class SvdAlgorithm implements Algorithm {
    private final SimpleMatrix w;
    private SimpleMatrix v;
    private SimpleMatrix q;

    @Override
    public SimpleMatrix getQuaternion() {
        return q;
    }

    public SvdAlgorithm(SimpleMatrix w0) {
        this.w = w0;
        this.v = w0;
    }

    @Override
    public void calculatePosition(double[] a, double[] m, double[] g) {
        double[] vec = new double[]{a[0],m[0],a[1],m[1],a[2],m[2]};
        this.v = new SimpleMatrix(3, 2, true, vec);

        SimpleMatrix B = w.mult(v.transpose());
        SimpleSVD usv = B.svd();
        SimpleMatrix M = SimpleMatrix.diag(1, 1, usv.getU().determinant() * usv.getV().determinant());
        SimpleMatrix R = (SimpleMatrix) usv.getU().mult(M).mult(usv.getV());
        this.q = DCMtoQuaternion(R);
    }

    private SimpleMatrix DCMtoQuaternion(SimpleMatrix dcm) {
        double tr = dcm.trace();
        double[] q = new double[4];
        double sqtrp1;
        double sqd1p1;
        SimpleMatrix d = dcm.diag();
        if (tr > 0) {
            sqtrp1 = Math.sqrt(tr + 1.0);

            q[0] = 0.5 * sqtrp1;
            q[1] = (dcm.get(1, 2) - dcm.get(2, 1)) / (2.0 * sqtrp1);
            q[2] = (dcm.get(2, 0) - dcm.get(0, 2)) / (2.0 * sqtrp1);
            q[3] = (dcm.get(0, 1) - dcm.get(1, 0)) / (2.0 * sqtrp1);
        } else {
            if ((d.get(1) > d.get(0)) && (d.get(1) > d.get(2))) {
                sqd1p1 = Math.sqrt(d.get(1) - d.get(0) - d.get(2) + 1.0);
                q[2] = 0.5 * sqd1p1;

                if (sqd1p1 != 0) {
                    sqd1p1 = 0.5 / sqd1p1;
                }

                q[0] = (dcm.get(2, 0) - dcm.get(0, 2)) * sqd1p1;
                q[1] = (dcm.get(0, 1) + dcm.get(1, 0)) * sqd1p1;
                q[3] = (dcm.get(1, 2) + dcm.get(2, 1)) * sqd1p1;
            } else if (d.get(2) > d.get(0)) {
                sqd1p1 = Math.sqrt(d.get(2) - d.get(0) - d.get(1) + 1.0);

                q[3] = 0.5 * sqd1p1;

                if (sqd1p1 != 0) {
                    sqd1p1 = 0.5 / sqd1p1;
                }

                q[0] = (dcm.get(0, 1) - dcm.get(1, 0)) * sqd1p1;
                q[1] = (dcm.get(2, 0) + dcm.get(0, 2)) * sqd1p1;
                q[2] = (dcm.get(1, 2) + dcm.get(2, 1)) * sqd1p1;
            } else {
                sqd1p1 = Math.sqrt(d.get(0) - d.get(1) - d.get(2) + 1.0);

                q[1] = 0.5 * sqd1p1;

                if (sqd1p1 != 0) {
                    sqd1p1 = 0.5 / sqd1p1;
                }

                q[0] = (dcm.get(1, 2) - dcm.get(2, 1)) * sqd1p1;
                q[2] = (dcm.get(0, 1) + dcm.get(1, 0)) * sqd1p1;
                q[3] = (dcm.get(2, 0) + dcm.get(0, 2)) * sqd1p1;
            }
        }
        return new SimpleMatrix(4, 1, true, q);
    }
}
