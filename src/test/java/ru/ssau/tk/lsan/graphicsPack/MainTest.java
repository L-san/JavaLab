package ru.ssau.tk.lsan.graphicsPack;

import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import ru.ssau.tk.lsan.graphicsPack.algorithms.Algorithm;
import ru.ssau.tk.lsan.graphicsPack.algorithms.Madgwick;
import ru.ssau.tk.lsan.graphicsPack.socket.Client;
import ru.ssau.tk.lsan.graphicsPack.socket.Client2Plotter;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;

import static org.testng.Assert.*;

public class MainTest extends Application {
    Point3D axis = new Point3D(0d, 1d, 0d);

    @Override
    public void start(Stage stage) {
        World myWorld = new World();
        Box box = myWorld.newBox();
        Group root = new Group();
        SmartGroup group = new SmartGroup();
        Text label = myWorld.newText();
        group.getChildren().add(box);
        root.getChildren().add(group);
        root.getChildren().add(label);
        Scene scene = new Scene(root, 1280, 720);

        Timer timer = new Timer("tim", true);
        TimerTask task = new TimerTask() {
            public void run() {
                group.rotateByAxis( 45d, axis);
                axis = new Point3D(-1, 0d, 0);
            }
        };

        long delay = 1000L;
        long period = 1000L;
        timer.scheduleAtFixedRate(task, delay, period);


        PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(0);
        scene.setCamera(camera);
        stage.setTitle("Depression Cube");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    class SmartGroup extends Group {
        Rotate r;
        Transform t = new Rotate();

        void rotateByAxis(double angle, Point3D axis) {
            r =  new Rotate(angle,axis);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }

    }
}