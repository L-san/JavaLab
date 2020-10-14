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

    @Override
    public boolean isPalindromic(String str) {
        String invStr = new StringBuilder(str).reverse().toString();
        return str.equals(invStr);
    }

    @Override
    public boolean isEqualStr(String str1, String str2) {
        if (str1 != null && str2 != null) {
            return str1.equalsIgnoreCase(str2);
        } else {
            return false;
        }
    }

    @Override
    public void symbolStrTest() {
        int i = 0;
        System.out.println("Символ\t№" + ++i);
        System.out.println("Символ\b№" + ++i);
        System.out.println("Символ\n№" + ++i);
        System.out.println("Символ\r№" + ++i);
        System.out.println("Символ\'№" + ++i);
        System.out.println("Символ\"№" + ++i);
        System.out.println("Символ\\№" + ++i);

    }

    @Override
    public int findSubStringIndex(String str1, String str2) {
        return str1.indexOf(str2);
    }
}
