package ru.ssau.tk.lsan.graphicsPack;

import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import ru.ssau.tk.lsan.graphicsPack.algorithms.Algorithm;
import ru.ssau.tk.lsan.graphicsPack.algorithms.KalmanFilter;

import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;

import static java.lang.String.format;

public class RotationManager extends Thread {
    private final KalmanFilter filterG = new KalmanFilter(50,0);
    private final KalmanFilter filterA = new KalmanFilter(60,0);
    private final KalmanFilter filterM = new KalmanFilter(60,0);
    private static ArrayBlockingQueue<Byte> exchanger;
    private static ArrayBlockingQueue<String> messageExchanger;
    protected byte[] message;
    protected World world;
    protected Box box;
    protected Algorithm algorithm;
    protected Text label;
    private final double time;
    private double[] q_prev;
    private double prev_angle = 0;

    public RotationManager(ArrayBlockingQueue<Byte> ex,ArrayBlockingQueue<String> message ,World tWorld, Box tBox, Text tLabel, Algorithm initial0, int len, double time0) {
        exchanger = ex;
        messageExchanger = message;
        this.world = tWorld;
        this.box = tBox;
        this.message = new byte[len];
        this.algorithm = initial0;
        this.label = tLabel;
        this.time = time0;
        this.q_prev = new double[]{1,0,0,0};
    }

    public void updateNodes(double duration) throws InterruptedException {
        double[] a = parser(0, 1);
        double[] m = parser(12, 1);
        double[] g = parser(6, 1);
        filterG.doFiltering(g);
        filterA.doFiltering(a);
        filterM.doFiltering(m);
        double[] g0 = filterG.getX_hat();
        double[] a0 = filterA.getX_hat();
        double[] m0 = filterM.getX_hat();
        messageExchanger.put(g[0]+" "+g[1]+" "+g[2]+" "+g0[0]+" "+g0[1]+" "+g0[2]);
       // messageExchanger.put(m[0]+" "+m[1]+" "+m[2]+" "+m0[0]+" "+m0[1]+" "+m0[2]);
        algorithm.calculatePosition(a0, m0, g0);
        //algorithm.calculatePosition(a, m, g);
        double[] q = algorithm.getQuaternion();

        double angle = Math.acos(q[0]);
        double q1,q2,q3;
        if (Math.sin(angle)!=0){
            q1 = q[1] / Math.sin(angle);
            q2 = q[2] / Math.sin(angle);
            q3 = q[3] / Math.sin(angle);
            q_prev = new double[]{angle,q1,q2,q3};
        }else{
            q1 = q_prev[1];
            q2 = q_prev[2];
            q3 = q_prev[3];
        }
        //Point3D rotationAxis = new Point3D(q1,q2,q3);
        Point3D rotationAxis = new Point3D(-q2, q3, q1);
        double angle0 = angle - prev_angle;
        prev_angle = angle;

        world.rotateBox(box, duration, angle0*180/Math.PI, rotationAxis);
        world.updateText(label, getString(a,m,g));
        System.out.println("angle: "+angle0*180/Math.PI+" q1: "+q1+" q2: "+q2+" q3: "+q3);
    }

    @Override
    public void run() {
        try {
            Runnable updater = () -> {
                if (message != null) {
                    try {
                        updateNodes(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            try {
                while (true) {
                    //while (exchanger.take() != (byte)1) ;
                    for (int i = 0; i < message.length; i++) {
                        message[i] = exchanger.take();
                        //System.out.print(message[i] + " ");
                    }
                    //System.out.print('\n');
                    Platform.runLater(updater);
                }

            } finally {
                System.out.println("Rotation stopped...");
            }

        } catch (InterruptedException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private double[] parser(int index, int endian) {
        //endian==0 hb lb endian==1 lb hb
        int j = 0;
        byte[] out = new byte[]{message[index], message[index + 1], message[index + 2], message[index + 3], message[index + 4], message[index + 5]};
        double[] outDouble = new double[message.length / 6];
        if (endian == 0) {
            for (int k = 2; k < message.length / 2-2; k += 2) {
                outDouble[j++] = toInt(out[k - 2], out[k - 1]);
            }
        } else if (endian == 1) {
            for (int k = 2; k < message.length /2-2; k += 2) {
                outDouble[j++] = toInt(out[k - 1], out[k - 2]);
            }
        }
        return outDouble;
    }

    public int toInt(byte hb, byte lb) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[]{hb, lb});
        short ans = bb.getShort();
        return ans;
    }

    public String getString(double[] a, double[] m, double[] g) {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < g.length; i++) {
            String res = format("%.4f",g[i]);
            strBuilder.append("g").append(i).append(": ").append(res).append(" ");
        }
        strBuilder.append('\n');
        for (int i = 0; i < g.length; i++) {
            String res = format("%.4f",a[i]);
            strBuilder.append("a").append(i).append(": ").append(res).append(" ");
        }
        strBuilder.append('\n');
        for (int i = 0; i < g.length; i++) {
            String res = format("%.4f",m[i]);
            strBuilder.append("m").append(i).append(": ").append(res).append(" ");
        }
        strBuilder.append('\n');
        return  strBuilder.toString();
    }
}
