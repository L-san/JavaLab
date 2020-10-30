package ru.ssau.tk.lsan.graphicsPack;

import javafx.application.Platform;
import javafx.scene.shape.Box;

import java.util.concurrent.Exchanger;

public class RotationManager extends Thread {
    private static Exchanger<Double> exchanger;
    protected Double message;
    protected Double previousMsg = 0d;
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
            System.out.println(previousMsg);
            Runnable updater = () -> {
                if (message != null) {
                    previousMsg = previousMsg + message;
                    updateNodes(1d, previousMsg);
                }
            };
            while(true){
                message = exchanger.exchange(message);
                System.out.println(previousMsg);
                Platform.runLater(updater);
            }

        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }


}
