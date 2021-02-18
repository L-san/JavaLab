package ru.ssau.tk.lsan.graphicsPack;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
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

        double zeta = Math.sqrt(3d/4d)*Math.PI/180*(0.0f);
        double beta = Math.sqrt(3d/4d)*Math.PI/180*0*(0.0f);

        double[] q_est = new double[]{1, 0, 0, 0};
        double delta_T = 1d / 100d;

        Algorithm initial = new Madgwick(q_est, beta, zeta, delta_T);

        Thread rotationManager = new RotationManager(exchanger, messageExchanger, myWorld, box, label, initial, 18);
        clientSocketThread.setDaemon(true);
        rotationManager.setDaemon(true);
        client2PlotterThread.setDaemon(true);
        clientSocketThread.start();
        rotationManager.start();
        //client2PlotterThread.start();

        PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(0);
        scene.setCamera(camera);
        stage.setTitle("Depression Cube");
        stage.setScene(scene);
        stage.show();
    }

    public Pane upperPane() {
        Text portId = new Text();
        Text ipAddress = new Text();

        Button okButton = new Button("Ok");

        //okButton.add
        return new FlowPane();
    }


    public static void main(String[] args) {
        launch(args);
        /*ArrayBlockingQueue<Byte> exchanger = new ArrayBlockingQueue<>(18);
        Thread clientSocketThread = new Client(exchanger);

        clientSocketThread.setDaemon(true);
        clientSocketThread.start();*/
    }
}