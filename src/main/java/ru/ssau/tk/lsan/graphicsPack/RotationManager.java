package ru.ssau.tk.lsan.graphicsPack;

import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import ru.ssau.tk.lsan.graphicsPack.algorithms.Algorithm;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;

import static java.lang.String.format;

public class RotationManager extends Thread {
    private static ArrayBlockingQueue<Byte> exchanger;
    private static ArrayBlockingQueue<String> messageExchanger;
    protected byte[] message;
    protected World world;
    protected Box box;
    protected Algorithm algorithm;
    protected Text label;

    public RotationManager(ArrayBlockingQueue<Byte> ex, ArrayBlockingQueue<String> message, World tWorld, Box tBox, Text tLabel, Algorithm initial0, int len) {
        exchanger = ex;
        messageExchanger = message;
        this.world = tWorld;
        this.box = tBox;
        this.message = new byte[len];
        this.algorithm = initial0;
        this.label = tLabel;
    }

    public void updateNodes() {
        double[] a = parser(0, 1);
        double[] m = parser(12, 1);
        double[] g = parser(6, 1);

        algorithm.calculatePosition(a, m, g);
        double[] q = algorithm.getQuaternion();

        //(/￣ー￣)/~~☆’.･.･:★’.･.･:☆
        double angle;
        angle = 2 * Math.acos(q[0]);
        Point3D rotationAxis = new Point3D(-q[2], q[3], q[1]);
        world.rotateBox(box, angle * 180 / Math.PI, rotationAxis);
        world.updateText(label, getString(a, m, g));
        System.out.println("angle: " + angle * 180 / Math.PI + " q0: " + q[0] + " q1: " + q[1] + " q2: " + q[2] + " q3: " + q[3]);
    }

    @Override
    public void run() {
        try {
            Runnable updater = () -> {
                if (message != null) {
                    updateNodes();
                }
            };
            try {
                while (true) {
                    for (int i = 0; i < message.length; i++) {
                        message[i] = exchanger.take();
                    }
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
            for (int k = 2; k < message.length / 2 - 2; k += 2) {
                outDouble[j++] = toInt(out[k - 2], out[k - 1]);
            }
        } else if (endian == 1) {
            for (int k = 2; k < message.length / 2 - 2; k += 2) {
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
            String res = format("%.4f", g[i]);
            strBuilder.append("g").append(i).append(": ").append(res).append(" ");
        }
        strBuilder.append('\n');
        for (int i = 0; i < g.length; i++) {
            String res = format("%.4f", a[i]);
            strBuilder.append("a").append(i).append(": ").append(res).append(" ");
        }
        strBuilder.append('\n');
        for (int i = 0; i < g.length; i++) {
            String res = format("%.4f", m[i]);
            strBuilder.append("m").append(i).append(": ").append(res).append(" ");
        }
        strBuilder.append('\n');
        return strBuilder.toString();
    }
}
