package ru.ssau.tk.lsan.graphicsPack;

import javafx.application.Application;
import javafx.scene.Group;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.stage.Stage;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;

//import java.io.IOException;

import javafx.scene.PerspectiveCamera;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Box box1 = new Box();

        box1.setWidth(300.0);
        box1.setHeight(300.0);
        box1.setDepth(300.0);
        PhongMaterial phongMaterial = new PhongMaterial();
        phongMaterial.setDiffuseColor(Color.POWDERBLUE);
        box1.setMaterial(phongMaterial);
        box1.setTranslateX(670);
        box1.setTranslateY(360);
        box1.setTranslateZ(360);
        box1.setDrawMode(DrawMode.FILL);
        Group root = new Group(box1);
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
