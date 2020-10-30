package ru.ssau.tk.lsan.graphicsPack;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import ru.ssau.tk.lsan.graphicsPack.Socket.Client;

import java.util.concurrent.Exchanger;

//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        World myWorld = new World();
        Box box = myWorld.newBox();
        Group root = new Group();
        root.getChildren().add(box);

        Scene scene = new Scene(root, 1280, 720);

        Exchanger<Double> exchanger = new Exchanger<>();
        Thread clientSocketThread = new Client(exchanger);
        Thread rotationManager = new RotationManager(exchanger, myWorld, box);

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
    }
}
