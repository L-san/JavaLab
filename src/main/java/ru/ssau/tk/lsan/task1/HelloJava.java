package ru.ssau.tk.lsan.task1;

public class HelloJava {
//TODO отдельные файлы для задачек, видимо. А то мэйн один, а вас много
    public static void main(String[] args) {
        //task 1.1------------------------------------------------------
        Person lSan = new Person();
        lSan.setFirstName("Lyudmila");
        lSan.setLastName("Seleznyova");
        lSan.setPassportId(5);
        System.out.println(lSan.getFirstName());
        System.out.println(lSan.getLastName());
        System.out.println(lSan.getPassportId());
        //--------------------------------------------------------------

        //task 1.2------------------------------------------------------
        Person human1 = new Person();
        Person human2 = new Person("firstName","lastName");
        Person human3 = new Person(5);
        Person human4 = new Person("firstName","lastName",5);
        //--------------------------------------------------------------

        //task 1.3------------------------------------------------------
        Point zeroPoint = new Point(0,0,0);
        Point eeePoint = new Point(Math.E,Math.E,Math.E);
        Point ottPoint = new Point(1,2,3);
        //--------------------------------------------------------------

        //task 1.4------------------------------------------------------
        Point sumAns = new Point(0,0,0);
        //sumAns = Points.sum(zeroPoint,eeePoint);
        //System.out.println("ans is "+sumAns.X+" "+sumAns.Y+" "+sumAns.Z);
        //sumAns = Points.substract(zeroPoint,eeePoint);
        //System.out.println("ans is "+sumAns.X+" "+sumAns.Y+" "+sumAns.Z);
        //sumAns = Points.multiply(zeroPoint,eeePoint);
        //System.out.println("ans is "+sumAns.X+" "+sumAns.Y+" "+sumAns.Z);
        //sumAns = Points.divide(zeroPoint,eeePoint);
        //System.out.println("ans is "+sumAns.X+" "+sumAns.Y+" "+sumAns.Z);
        //--------------------------------------------------------------

        //task 1.5------------------------------------------------------
        //sumAns = Points.enlarge(ottPoint, (double)2.0);
        //System.out.println("ans is "+sumAns.X+" "+sumAns.Y+" "+sumAns.Z);
        //--------------------------------------------------------------

        //task 1.6------------------------------------------------------
        //System.out.println("ans is "+ottPoint.length());
        //--------------------------------------------------------------

        //task 1.7------------------------------------------------------
        System.out.println("ans is "+Points.opposite(ottPoint).X);
        //--------------------------------------------------------------
    }
}
