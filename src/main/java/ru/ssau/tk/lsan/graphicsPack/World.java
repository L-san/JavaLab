package ru.ssau.tk.lsan.graphicsPack;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;

public class World {
    protected Box newBox() {
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
        return box1;
    }


    protected final Group getWorldObjectsAsGroup() {
        return new Group(newBox());
    }
}
