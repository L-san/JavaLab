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

    @Test
    public void testIsPalindrom() {
        MyString testStr = new MyString();
        assertEquals(testStr.isPalindrom("noon"), true);
        assertEquals(testStr.isPalindrom("deed"), true);
        assertEquals(testStr.isPalindrom("level"), true);
    }

    @Test
    public void testIsEqualStr() {
        MyString testStr = new MyString();
        assertEquals(testStr.isEqualStr("Van Gough is an incredible painter","van gough is an incredible painter"), true);
        assertEquals(testStr.isEqualStr(null,null),false);
    }
}