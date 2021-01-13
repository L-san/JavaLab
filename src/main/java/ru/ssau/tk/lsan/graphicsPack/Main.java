package ru.ssau.tk.lsan.graphicsPack;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.ssau.tk.lsan.graphicsPack.algorithms.Algorithm;
import ru.ssau.tk.lsan.graphicsPack.algorithms.Madgwick;
import ru.ssau.tk.lsan.graphicsPack.socket.Client;
import ru.ssau.tk.lsan.graphicsPack.socket.Client2Plotter;

import java.util.concurrent.ArrayBlockingQueue;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        World myWorld = new World();
        Box box = myWorld.newBox();
        Group root = new Group();
        Text label = myWorld.newText();
        root.getChildren().add(box);
        root.getChildren().add(label);
        Scene scene = new Scene(root, 1280, 720);
        ArrayBlockingQueue<Byte> exchanger = new ArrayBlockingQueue<>(18);
        Thread clientSocketThread = new Client(exchanger);
        ArrayBlockingQueue<String> messageExchanger = new ArrayBlockingQueue<>(1);
        Thread client2PlotterThread = new Client2Plotter(messageExchanger);

        double gyroMeasError = 3.14159265358979 * (0.01f / 180.0f);
        double gyroMeasDrift = 3.14159265358979 * (1.0f / 180.0f);
        double zeta = Math.sqrt(3.0f / 4.0f) * gyroMeasDrift;
        double beta = Math.sqrt(3.0f / 4.0f) * gyroMeasError;
        //double zeta = 0;
        //double beta = 0;

        double[] q_est = new double[]{1, 0, 0, 0};
        double delta_T = 1d/143d;

        Algorithm initial = new Madgwick(q_est, beta, zeta, delta_T);

        Thread rotationManager = new RotationManager(exchanger,messageExchanger, myWorld, box, label, initial, 18,delta_T);
        clientSocketThread.setDaemon(true);
        rotationManager.setDaemon(true);
        client2PlotterThread.setDaemon(true);
        clientSocketThread.start();
        rotationManager.start();
        client2PlotterThread.start();

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
        /*ArrayBlockingQueue<Byte> exchanger = new ArrayBlockingQueue<>(18);
        Thread clientSocketThread = new Client(exchanger);

        clientSocketThread.setDaemon(true);
        clientSocketThread.start();*/
    }
}