package ru.ssau.tk.lsan.graphicsPack;

import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import org.ejml.simple.SimpleMatrix;
import ru.ssau.tk.lsan.graphicsPack.algorithms.Algorithm;
import ru.ssau.tk.lsan.graphicsPack.operations.LinearAlgebraOperations;

import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;

import static java.lang.String.format;

public class RotationManager extends Thread {
    private static ArrayBlockingQueue<Byte> exchanger;
    protected byte[] message;
    protected World world;
    protected Box box;
    protected Algorithm initial;
    protected Text label;
    private final double time;
    private double[] q_prev;

    public RotationManager(ArrayBlockingQueue<Byte> ex, World tWorld, Box tBox, Text tLabel, Algorithm initial0, int len, double time0) {
        exchanger = ex;
        this.world = tWorld;
        this.box = tBox;
        this.message = new byte[len];
        this.initial = initial0;
        this.label = tLabel;
        this.time = time0;
        this.q_prev = new double[]{1,0,0,0};
    }

    public void updateNodes(double duration) {
        double[] a = parser(0, 1);
        double[] m = parser(12, 0);
        //double[] m = new double[]{m0[2],m0[1],m0[0]};
        double[] g = parser(6, 1);;
        initial.calculatePosition(a, m, g);
        double[] q = initial.getQuaternion();
        //q_prev = LinearAlgebraOperations.quat_mult(q_prev,q);

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
        Point3D rotationAxis = new Point3D(q1,q2,q3);
        //Point3D rotationAxis = new Point3D(-q2, q3, q1);
        world.rotateBox(box, duration, 2*angle*180/Math.PI, rotationAxis);
        world.updateText(label, getString(a,m,g));
        System.out.println("angle: "+2*angle*180/Math.PI+" q1: "+q1+" q2: "+q2+" q3: "+q3);
    }

    @Override
    public void run() {
        try {
            Runnable updater = () -> {
                if (message != null) {
                    updateNodes(time);
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
        return bb.getShort();
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
