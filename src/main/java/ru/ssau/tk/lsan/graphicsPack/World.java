package ru.ssau.tk.lsan.graphicsPack;

import javafx.animation.RotateTransition;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.text.Text;

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

    protected Text newText(){
        Text text = new Text();
        text.setX(50);
        text.setY(600);
        return text;
    }

    protected final void rotateBox(Box box, double angle, Point3D rotationAxis) {
        RotateTransition rotate = new RotateTransition();
        rotate.setByAngle(angle);
        rotate.setAxis(rotationAxis);
        rotate.setNode(box);
        rotate.play();
    }

    protected final void updateText(Text text, String str){
        text.setText(str);
    }
}
