package ru.ssau.tk.lsan.task3;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MyStringTest {

    @Test
    public void testPrintMyString() {
        MyString testStr = new MyString();
        testStr.printMyString("oh, here we go again");
    }

    @Test
    public void testGetMyBytes() {
        MyString testStr = new MyString();
        testStr.getMyBytes("lmao");
        testStr.getMyBytes("лмао");
    }

    @Test
    public void testMyVoid() {
        MyString testStr = new MyString();
        testStr.myVoid();
    }
}