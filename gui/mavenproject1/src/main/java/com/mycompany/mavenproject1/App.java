package com.mycompany.mavenproject1;

import javafx.application.Application;
import javafx.scene.Group; 
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import java.io.IOException;
import javafx.scene.PerspectiveCamera; 

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Box box1 = new Box();
        //Setting the properties of the Box 
         box1.setWidth(100.0); 
         box1.setHeight(100.0);   
         box1.setDepth(100.0); 
      
         //Setting the position of the box 
         box1.setTranslateX(300); 
         box1.setTranslateY(300); 
         box1.setTranslateZ(300);
      
        //Setting the drawing mode of the box 
         box1.setDrawMode(DrawMode.FILL); 
         //Creating a Group object   
        Group root = new Group(box1);
        scene = new Scene(root, 640, 480);
        //Setting camera 
        PerspectiveCamera camera = new PerspectiveCamera(false); 
        camera.setTranslateX(0); 
        camera.setTranslateY(0); 
        camera.setTranslateZ(0); 
        scene.setCamera(camera);  
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}