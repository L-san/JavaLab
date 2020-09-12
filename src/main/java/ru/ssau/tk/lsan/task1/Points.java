package ru.ssau.tk.lsan.task1;

public class Points {
    public static Point sum(Point firstPoint, Point secondPoint){
        return new Point(firstPoint.X+secondPoint.X,
                         firstPoint.Y+secondPoint.Y,
                         firstPoint.Z+secondPoint.Z);
    }

    public static Point substract(Point firstPoint, Point secondPoint){
        return new Point(firstPoint.X-secondPoint.X,
                         firstPoint.Y-secondPoint.Y,
                         firstPoint.Z-secondPoint.Z);
    }

    public static Point multiply(Point firstPoint, Point secondPoint){
        return new Point(firstPoint.X*secondPoint.X,
                         firstPoint.Y*secondPoint.Y,
                         firstPoint.Z*secondPoint.Z);
    }
}
