package ru.ssau.tk.lsan.task3;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MyStringTest {

    @Test
    public void testPrintMyString() {
        MyString testStr = new MyString();
        testStr.printMyString("oh, here we go again");
    }
}