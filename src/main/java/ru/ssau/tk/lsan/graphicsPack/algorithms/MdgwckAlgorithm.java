package ru.ssau.tk.lsan.graphicsPack.algorithms;

import org.ejml.simple.SimpleMatrix;

public class MdgwckAlgorithm implements Algorithm {
    private final double zeta;
    private final double beta;
    private double delta_T;
    private double SEq_1, SEq_2, SEq_3, SEq_4;
    private double w_bx = 0, w_by = 0, w_bz = 0;
    double b_x = 1, b_z = 0;

    public MdgwckAlgorithm() {
        this.beta = 0d;
        this.zeta = 0d;
    }

    public MdgwckAlgorithm(double[] q_est_0, double beta0, double dzta0, double delta_T0) {
        this.beta = beta0;
        this.zeta = dzta0;
        this.delta_T = delta_T0;
        this.SEq_1 = q_est_0[0];
        this.SEq_2 = q_est_0[1];
        this.SEq_3 = q_est_0[2];
        this.SEq_4 = q_est_0[3];

    }

    @Override
    public SimpleMatrix getQuaternion() {
        return new SimpleMatrix(4, 1, true, new double[]{SEq_1, SEq_2, SEq_3, SEq_4});
    }

    @Override
    public void calculatePosition(double[] a, double[] m, double[] g) {
        double a_x = a[0], a_y = a[1], a_z = a[2];
        double w_x = g[0], w_y = g[1], w_z = g[2];
        double m_x = m[0], m_y = m[1], m_z = m[2];

        double norm;
        double SEqDot_omega_1, SEqDot_omega_2, SEqDot_omega_3, SEqDot_omega_4;
        double f_1, f_2, f_3, f_4, f_5, f_6;
        double J_11or24, J_12or23, J_13or22, J_14or21, J_32, J_33, J_41, J_42,
                J_43, J_44, J_51, J_52, J_53, J_54, J_61, J_62, J_63, J_64;
        double SEqHatDot_1, SEqHatDot_2, SEqHatDot_3, SEqHatDot_4;
        double w_err_x, w_err_y, w_err_z;
        double h_x, h_y, h_z;

        norm = Math.sqrt(a_x * a_x + a_y * a_y + a_z * a_z);
        a_x /= norm;
        a_y /= norm;
        a_z /= norm;

        norm = Math.sqrt(m_x * m_x + m_y * m_y + m_z * m_z);
        m_x /= norm;
        m_y /= norm;
        m_z /= norm;

        double k = Math.PI / (180 * (2 << 14));
        w_x *= k;
        w_y *= k;
        w_z *= k;

        double halfSEq_1 = 0.5f * SEq_1;
        double halfSEq_2 = 0.5f * SEq_2;
        double halfSEq_3 = 0.5f * SEq_3;
        double halfSEq_4 = 0.5f * SEq_4;
        double twoSEq_1 = 2.0f * SEq_1;
        double twoSEq_2 = 2.0f * SEq_2;
        double twoSEq_3 = 2.0f * SEq_3;
        double twoSEq_4 = 2.0f * SEq_4;
        double twob_x = 2.0f * b_x;
        double twob_z = 2.0f * b_z;
        double twob_xSEq_1 = 2.0f * b_x * SEq_1;
        double twob_xSEq_2 = 2.0f * b_x * SEq_2;
        double twob_xSEq_3 = 2.0f * b_x * SEq_3;
        double twob_xSEq_4 = 2.0f * b_x * SEq_4;
        double twob_zSEq_1 = 2.0f * b_z * SEq_1;
        double twob_zSEq_2 = 2.0f * b_z * SEq_2;
        double twob_zSEq_3 = 2.0f * b_z * SEq_3;
        double twob_zSEq_4 = 2.0f * b_z * SEq_4;
        double SEq_1SEq_2;
        double SEq_1SEq_3 = SEq_1 * SEq_3;
        double SEq_1SEq_4;
        double SEq_2SEq_3;
        double SEq_2SEq_4 = SEq_2 * SEq_4;
        double SEq_3SEq_4;
        double twom_x = 2.0f * m_x;
        double twom_y = 2.0f * m_y;
        double twom_z = 2.0f * m_z;

        f_1 = twoSEq_2 * SEq_4 - twoSEq_1 * SEq_3 - a_x;
        f_2 = twoSEq_1 * SEq_2 + twoSEq_3 * SEq_4 - a_y;
        f_3 = 1.0f - twoSEq_2 * SEq_2 - twoSEq_3 * SEq_3 - a_z;
        f_4 = twob_x * (0.5f - SEq_3 * SEq_3 - SEq_4 * SEq_4) + twob_z * (SEq_2SEq_4 - SEq_1SEq_3) - m_x;
        f_5 = twob_x * (SEq_2 * SEq_3 - SEq_1 * SEq_4) + twob_z * (SEq_1 * SEq_2 + SEq_3 * SEq_4) - m_y;
        f_6 = twob_x * (SEq_1SEq_3 + SEq_2SEq_4) + twob_z * (0.5f - SEq_2 * SEq_2 - SEq_3 * SEq_3) - m_z;
        J_11or24 = twoSEq_3;
        J_12or23 = 2.0f * SEq_4;
        J_13or22 = twoSEq_1;
        J_14or21 = twoSEq_2;
        J_32 = 2.0f * J_14or21;
        J_33 = 2.0f * J_11or24;
        J_41 = twob_zSEq_3;
        J_42 = twob_zSEq_4;
        J_43 = 2.0f * twob_xSEq_3 + twob_zSEq_1;
        J_44 = 2.0f * twob_xSEq_4 - twob_zSEq_2;
        J_51 = twob_xSEq_4 - twob_zSEq_2;
        J_52 = twob_xSEq_3 + twob_zSEq_1;
        J_53 = twob_xSEq_2 + twob_zSEq_4;
        J_54 = twob_xSEq_1 - twob_zSEq_3;
        J_61 = twob_xSEq_3;
        J_62 = twob_xSEq_4 - 2.0f * twob_zSEq_2;
        J_63 = twob_xSEq_1 - 2.0f * twob_zSEq_3;
        J_64 = twob_xSEq_2;

        SEqHatDot_1 = J_14or21 * f_2 - J_11or24 * f_1 - J_41 * f_4 - J_51 * f_5 + J_61 * f_6;
        SEqHatDot_2 = J_12or23 * f_1 + J_13or22 * f_2 - J_32 * f_3 + J_42 * f_4 + J_52 * f_5 + J_62 * f_6;
        SEqHatDot_3 = J_12or23 * f_2 - J_33 * f_3 - J_13or22 * f_1 - J_43 * f_4 + J_53 * f_5 + J_63 * f_6;
        SEqHatDot_4 = J_14or21 * f_1 + J_11or24 * f_2 - J_44 * f_4 - J_54 * f_5 + J_64 * f_6;

        norm = Math.sqrt(SEqHatDot_1 * SEqHatDot_1 + SEqHatDot_2 * SEqHatDot_2 + SEqHatDot_3 * SEqHatDot_3 + SEqHatDot_4 * SEqHatDot_4);
        SEqHatDot_1 = SEqHatDot_1 / norm;
        SEqHatDot_2 = SEqHatDot_2 / norm;
        SEqHatDot_3 = SEqHatDot_3 / norm;
        SEqHatDot_4 = SEqHatDot_4 / norm;
        w_err_x = twoSEq_1 * SEqHatDot_2 - twoSEq_2 * SEqHatDot_1 - twoSEq_3 * SEqHatDot_4 + twoSEq_4 * SEqHatDot_3;
        w_err_y = twoSEq_1 * SEqHatDot_3 + twoSEq_2 * SEqHatDot_4 - twoSEq_3 * SEqHatDot_1 - twoSEq_4 * SEqHatDot_2;
        w_err_z = twoSEq_1 * SEqHatDot_4 - twoSEq_2 * SEqHatDot_3 + twoSEq_3 * SEqHatDot_2 - twoSEq_4 * SEqHatDot_1;
        w_bx += w_err_x * delta_T * zeta;
        w_by += w_err_y * delta_T * zeta;
        w_bz += w_err_z * delta_T * zeta;
        w_x -= w_bx;
        w_y -= w_by;
        w_z -= w_bz;
        SEqDot_omega_1 = -halfSEq_2 * w_x - halfSEq_3 * w_y - halfSEq_4 * w_z;
        SEqDot_omega_2 = halfSEq_1 * w_x + halfSEq_3 * w_z - halfSEq_4 * w_y;
        SEqDot_omega_3 = halfSEq_1 * w_y - halfSEq_2 * w_z + halfSEq_4 * w_x;
        SEqDot_omega_4 = halfSEq_1 * w_z + halfSEq_2 * w_y - halfSEq_3 * w_x;
        SEq_1 += (SEqDot_omega_1 - (beta * SEqHatDot_1)) * delta_T;
        SEq_2 += (SEqDot_omega_2 - (beta * SEqHatDot_2)) * delta_T;
        SEq_3 += (SEqDot_omega_3 - (beta * SEqHatDot_3)) * delta_T;
        SEq_4 += (SEqDot_omega_4 - (beta * SEqHatDot_4)) * delta_T;
        norm = Math.sqrt(SEq_1 * SEq_1 + SEq_2 * SEq_2 + SEq_3 * SEq_3 + SEq_4 * SEq_4);
        SEq_1 /= norm;
        SEq_2 /= norm;
        SEq_3 /= norm;
        SEq_4 /= norm;
        SEq_1SEq_2 = SEq_1 * SEq_2;
        SEq_1SEq_3 = SEq_1 * SEq_3;
        SEq_1SEq_4 = SEq_1 * SEq_4;
        SEq_3SEq_4 = SEq_3 * SEq_4;
        SEq_2SEq_3 = SEq_2 * SEq_3;
        SEq_2SEq_4 = SEq_2 * SEq_4;
        h_x = twom_x * (0.5f - SEq_3 * SEq_3 - SEq_4 * SEq_4) + twom_y * (SEq_2SEq_3 - SEq_1SEq_4) + twom_z * (SEq_2SEq_4 + SEq_1SEq_3);
        h_y = twom_x * (SEq_2SEq_3 + SEq_1SEq_4) + twom_y * (0.5f - SEq_2 * SEq_2 - SEq_4 * SEq_4) + twom_z * (SEq_3SEq_4 - SEq_1SEq_2);
        h_z = twom_x * (SEq_2SEq_4 - SEq_1SEq_3) + twom_y * (SEq_3SEq_4 + SEq_1SEq_2) + twom_z * (0.5f - SEq_2 * SEq_2 - SEq_3 * SEq_3);
        b_x = Math.sqrt((h_x * h_x) + (h_y * h_y));
        b_z = h_z;
    }


}
