package ru.ssau.tk.lsan.task1;

import static java.lang.Math.sqrt;

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

    public static Point divide(Point firstPoint, Point secondPoint){
        return new Point(firstPoint.X/secondPoint.X,
                         firstPoint.Y/secondPoint.Y,
                         firstPoint.Z/secondPoint.Z);
    }

    public static Point enlarge(Point firstPoint, double secondPoint){
        return Points.multiply(firstPoint,new Point(secondPoint,secondPoint,secondPoint));
    }

    public static double length(Point point){
        return point.length();
    }

    public static Point opposite(Point point){
        return multiply(point, new Point(-1,-1,-1));
    }

    public static Point inverse(Point point){
        return divide(new Point(1,1,1), point);
    }

    public static Point vectorProduct(Point firstPoint, Point secondPoint){
        return new Point(firstPoint.Y*secondPoint.Z-firstPoint.Z*secondPoint.Y,
                         firstPoint.Z*secondPoint.X-firstPoint.X*secondPoint.Z,
                         firstPoint.X*secondPoint.Y-firstPoint.Y*secondPoint.X);
    }
}
