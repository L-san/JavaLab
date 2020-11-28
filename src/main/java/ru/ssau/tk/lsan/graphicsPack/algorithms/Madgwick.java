package ru.ssau.tk.lsan.graphicsPack.algorithms;

import ru.ssau.tk.lsan.graphicsPack.operations.KalmanFilter;

public class Madgwick implements Algorithm {
    private final double zeta;
    private final double beta;
    private double dt;
    private double[] q_est;


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
        double wx,wy,wz;
        //System.out.println(q_est0+" "+ q_est1+" "+q_est2+" "+q_est3);
        double k = Math.PI / (180 *(2 << 13));
        wx = g[0]*k; wy = g[1]*k; wz = g[2]*k;
        System.out.println(wx+" "+wy+" "+wz);
        //и только потом уже градиентный спуск!!!
        //потом корректировки для омеги
        //puassons equations at first
        q_est[0] = q_est[0]-0.5*(q_est[1]*wx + q_est[2]*wy + q_est[3]*wz)*dt;
        q_est[1] = q_est[1]+0.5*(q_est[0]*wx + q_est[2]*wz - q_est[3]*wy)*dt;
        q_est[2] = q_est[2]+0.5*(q_est[0]*wy + q_est[3]*wx - q_est[1]*wz)*dt;
        q_est[3] = q_est[3]+0.5*(q_est[0]*wz + q_est[1]*wy - q_est[2]*wx)*dt;


    }
}
