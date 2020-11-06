package ru.ssau.tk.lsan.graphicsPack;

import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.shape.Box;
import org.ejml.simple.SimpleMatrix;
import ru.ssau.tk.lsan.graphicsPack.algorithms.Algorithm;

import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;

public class RotationManager extends Thread {
    private static ArrayBlockingQueue<Byte> exchanger;
    protected byte[] message;
    protected World world;
    protected Box box;
    protected Algorithm initial;

    public RotationManager(ArrayBlockingQueue<Byte> ex, World tWorld, Box tBox, Algorithm initial0, int len) {
        exchanger = ex;
        this.world = tWorld;
        this.box = tBox;
        this.message = new byte[len];
        this.initial = initial0;
    }

    public void updateNodes(double duration) {
        double[] a = normalizeVector(parser(0, 1));
        double[] m = normalizeVector(parser(12, 0));
        double[] g = parser(6, 1);
        for (int i = 0; i < g.length; i++) {
            System.out.print("g" + i + ":" + g[i] + " ");
        }
        System.out.print('\n');
        for (int i = 0; i < g.length; i++) {
            System.out.print("a" + i + ":" + a[i] + " ");
        }
        System.out.print('\n');
        for (int i = 0; i < g.length; i++) {
            System.out.print("m" + i + ":" + m[i] + " ");
        }
        initial.calculatePosition(a, g, m);
        SimpleMatrix q = initial.getQuaternion();
        double angle = Math.acos(q.get(0)) * 2;
        double q1 = -q.get(1) / Math.sin(angle / 2);
        double q2 = -q.get(2) / Math.sin(angle / 2);
        double q3 = -q.get(3) / Math.sin(angle / 2);
        Point3D rotationAxis = new Point3D(q1, q2, q3);
        world.rotateBox(box, duration, angle, rotationAxis);
        // System.out.println(q[0]);
    }

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

    @Override
    public void run() {
        try {
            Runnable updater = () -> {
                if (message != null) {
                    updateNodes(0.1d);
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
                outDouble[j++] = (double) toInt(out[k - 2], out[k - 1]);
            }
        } else if (endian == 1) {
            for (int k = 2; k < message.length /2-2; k += 2) {
                outDouble[j++] = (double) toInt(out[k - 1], out[k - 2]);
            }
        }
        return outDouble;
    }

    public int toInt(byte hb, byte lb) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[]{hb, lb});
        return bb.getShort();
    }
}
