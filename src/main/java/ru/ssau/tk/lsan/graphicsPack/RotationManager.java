package ru.ssau.tk.lsan.graphicsPack;

import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.shape.Box;
import org.ejml.simple.SimpleMatrix;
import ru.ssau.tk.lsan.graphicsPack.algorithms.Algorithm;

import java.util.concurrent.ArrayBlockingQueue;

public class RotationManager extends Thread {
    private static ArrayBlockingQueue<Double> exchanger;
    protected double[] message;
    protected World world;
    protected Box box;
    protected Algorithm initial;

    public RotationManager(ArrayBlockingQueue<Double> ex, World tWorld, Box tBox, Algorithm initial0, int len) {
        exchanger = ex;
        this.world = tWorld;
        this.box = tBox;
        this.message = new double[len];
        this.initial = initial0;
    }

    public void updateNodes(double duration) {
        initial.calculatePosition(new double[]{message[0],message[1],message[2]},new double[]{message[6],message[7],message[8]},new double[]{message[3],message[4],message[5]});
        SimpleMatrix q = initial.getQuaternion();
        double angle = Math.acos(q.get(0))*2;
        double q1 = -q.get(1)/Math.sin(angle/2);
        double q2 = -q.get(2)/Math.sin(angle/2);
        double q3 = -q.get(3)/Math.sin(angle/2);
        Point3D rotationAxis = new Point3D(q1,q2,q3);
        world.rotateBox(box, duration, angle,rotationAxis);
       // System.out.println(q[0]);
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
                    while(exchanger.take()!=666d);
                    for (int i = 0; i < message.length; i++) {
                        message[i] = exchanger.take();
                        //System.out.print(message[i]+" ");
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
}
