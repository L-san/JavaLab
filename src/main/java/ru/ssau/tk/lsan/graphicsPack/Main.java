package ru.ssau.tk.lsan.graphicsPack;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import org.ejml.simple.SimpleMatrix;
import ru.ssau.tk.lsan.graphicsPack.algorithms.Algorithm;
import ru.ssau.tk.lsan.graphicsPack.algorithms.MadgwickAlgorithm;
import ru.ssau.tk.lsan.graphicsPack.algorithms.SvdAlgorithm;
import ru.ssau.tk.lsan.graphicsPack.socket.Client;

import java.util.concurrent.ArrayBlockingQueue;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        World myWorld = new World();
        Box box = myWorld.newBox();
        Group root = new Group();
        root.getChildren().add(box);

        Scene scene = new Scene(root, 1280, 720);
        ArrayBlockingQueue<Byte> exchanger = new ArrayBlockingQueue<>(18);
        Thread clientSocketThread = new Client(exchanger);

        double dzta = 8.660254037844386e-03;
        double bta = 8.660254037844386e-03;
        double[] q_est = new double[]{1, 0, 0, 0};
        double omega_eps_prev = 0;
        double delta_T = 0.1;
        double[] m = new double[]{0.3040, 0.0654, 0.9504};
        double[] g = new double[]{0, 0, -1};
        double[] vector = new double[]{g[0],m[0],g[1],m[1],g[2],m[2]};
        SimpleMatrix initVec = new SimpleMatrix(3,2,true,vector);
        Algorithm initial = new MadgwickAlgorithm(q_est,omega_eps_prev,bta,dzta,delta_T);
        //Algorithm initial = new SvdAlgorithm(initVec);
        Thread rotationManager = new RotationManager(exchanger, myWorld, box, initial, 18);

        clientSocketThread.setDaemon(true);
        rotationManager.setDaemon(true);
        clientSocketThread.start();
        rotationManager.start();

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