package ru.ssau.tk.lsan.graphicsPack;

import javafx.animation.RotateTransition;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class World {
    protected Box newBox() {
        Box box = new Box();
        box.setWidth(300.0);
        box.setHeight(300.0);
        box.setDepth(300.0);
        PhongMaterial phongMaterial = new PhongMaterial();
        phongMaterial.setDiffuseColor(Color.POWDERBLUE);
        box.setMaterial(phongMaterial);

        box.setTranslateX(670);
        box.setTranslateY(360);
        box.setTranslateZ(360);
        box.setDrawMode(DrawMode.FILL);
        return box;
    }

    protected final void rotateBox(Box box, double duration, double angle) {
        RotateTransition rotate = new RotateTransition();
        Point3D rotationAxis = new Point3D(1d,1d,1d);
        box.setRotationAxis(rotationAxis);
        //box.setRotate(angle);
        rotate.setByAngle(angle);
        //rotate.setCycleCount(500);
        rotate.setDuration(Duration.millis(1000));
        //rotate.setDuration(Duration.millis(duration));
        //rotate.setAutoReverse(true);
        rotate.setNode(box);
        rotate.play();
    }
}
