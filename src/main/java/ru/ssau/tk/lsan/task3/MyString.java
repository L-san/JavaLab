package ru.ssau.tk.lsan.task3;

public class MyString implements MyStringMethods{
    @Override
    public void printMyString(String str) {
        for(int i = 0; i<str.length(); i++){
            System.out.println(str.charAt(i));
        }
    }
}
