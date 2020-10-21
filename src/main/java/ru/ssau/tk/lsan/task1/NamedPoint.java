package ru.ssau.tk.lsan.task1;

public class NamedPoint extends Point{
    private String name;

    NamedPoint(double X, double Y, double Z, String name) {
         super(X,Y,Z);
         this.name=name;
    }

    NamedPoint(double X, double Y, double Z) {
        super(X,Y,Z);
    }
    NamedPoint(){
        this(0, 0, 0, "Origin");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
