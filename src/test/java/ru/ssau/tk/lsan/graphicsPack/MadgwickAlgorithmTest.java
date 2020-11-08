package ru.ssau.tk.lsan.graphicsPack;

import org.ejml.simple.SimpleMatrix;
import org.testng.annotations.Test;
import ru.ssau.tk.lsan.graphicsPack.algorithms.MadgwickAlgorithm;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class MadgwickAlgorithmTest {

    protected final MadgwickAlgorithm test = new MadgwickAlgorithm();
    private double delta = 0.001;
    @Test
    public void testCalculatePosition() {
        double dzta = 8.660254037844386e-04;
        double bta = 8.660254037844386e-04;
        double[] q_est = new double[]{1,0,0,0};
        double[] omega_eps_prev = new double[]{0,0,0,0};;
        double delta_T = 0.001;

        MadgwickAlgorithm realTest = new MadgwickAlgorithm(q_est,omega_eps_prev,bta,dzta,delta_T);
        SimpleMatrix q = realTest.getQuaternion();
        realTest.calculatePosition(new double[]{1,0,0}, new double[]{0,0,1}, new double[]{0,0,0});
        SimpleMatrix qCalc = realTest.getQuaternion();
        for(int i =0;i<4;i++){
            assertEquals(q.get(i),q_est[i],delta);
            assertEquals(qCalc.get(i),q_est[i],delta);
        }
    }


}