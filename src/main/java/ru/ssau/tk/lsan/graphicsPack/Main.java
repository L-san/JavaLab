package ru.ssau.tk.lsan.graphicsPack;

import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

//import java.io.IOException;

import javafx.scene.PerspectiveCamera;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Group root = new World().getWorldObjectsAsGroup();
        Scene scene = new Scene(root, 1280, 720);



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
