package ru.ssau.tk.lsan.task1;

public class PrintSmth {
    public void printType(byte arg) {
        System.out.println("byte");
    }

    public void printType(char arg) {
        System.out.println("char");
    }

    public void printType(short arg) {
        System.out.println("short");
    }

    public void printType(int arg) {
        System.out.println("int");
    }

    public void printType(long arg) {
        System.out.println("long");
    }

    public void printType(float arg) {
        System.out.println("float");
    }

    public void printType(double arg) {
        System.out.println("double");
    }

    public void printType(boolean arg) {
        System.out.println("boolean");
    }
    public void printType(Object arg){
        if(arg==null){
            System.out.println("null");
        }else{
            System.out.println(arg.getClass());
        }
    }
}
