package ru.ssau.tk.lsan.task3;

public class MyString implements MyStringMethods {
    @Override
    public void printMyString(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
        }
    }

    @Override
    public void getMyBytes(String str) {
        byte[] byteArr = str.getBytes();
        for (byte i : byteArr) {
            System.out.println(i);
        }
    }

    @Override
    public void myVoid() {
        String str = "Internal void";
        String obj = new String(str);
        System.out.println("== operator answer is:" + (str == obj));
        System.out.println("equals() answer is:" + (str.equals(obj)));
    }
}
