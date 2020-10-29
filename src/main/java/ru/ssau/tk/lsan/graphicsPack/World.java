package ru.ssau.tk.lsan.graphicsPack;

import javafx.animation.RotateTransition;
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

        RotateTransition rotate = new RotateTransition();
        box.setRotationAxis(Rotate.Y_AXIS);
        rotate.setByAngle(360);
        rotate.setCycleCount(500);
        rotate.setDuration(Duration.millis(10000));
        box.setRotate(100);
        rotate.setAutoReverse(true);
        rotate.setNode(box);
        rotate.play();

        return box;
    }


    protected final Group getWorldObjectsAsGroup() {
        return new Group(newBox());
    }
}
