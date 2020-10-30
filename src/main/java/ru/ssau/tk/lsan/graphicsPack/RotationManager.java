package ru.ssau.tk.lsan.graphicsPack;

import javafx.application.Platform;
import javafx.scene.shape.Box;

import java.util.concurrent.Exchanger;

public class RotationManager extends Thread {
    private static Exchanger<Double> exchanger;
    protected Double message;
    protected World world;
    protected Box box;

    public RotationManager(Exchanger<Double> ex, World tWorld, Box tBox) {
        exchanger = ex;
        this.world = tWorld;
        this.box = tBox;
    }

    public void updateNodes(double duration, double angle) {
        world.rotateBox(box, duration, angle);
    }

    @Override
    public void run() {
        try {
            message = exchanger.exchange(message);
            Runnable updater = () -> {
                if (message != null) {
                    updateNodes(1d, message);
                }
            };
            try {
                while (true) {
                    message = exchanger.exchange(message);
                    if (message == -1) {
                        throw new IllegalArgumentException("There's no input data");
                    }
                    System.out.println(message);
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
