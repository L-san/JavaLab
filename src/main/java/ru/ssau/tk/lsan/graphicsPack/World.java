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

    protected final void rotateBox(Box box, double duration, double angle, Point3D rotationAxis) {
        RotateTransition rotate = new RotateTransition();
        rotate.setByAngle(angle);
        //rotate.setCycleCount(500);
        //rotate.setDuration(Duration.millis(duration));
        rotate.setAxis(rotationAxis);
        rotate.setAutoReverse(true);
        rotate.setNode(box);
        rotate.play();
    }
}
