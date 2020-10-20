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
    public void testIsPalindromic() {
        MyString testStr = new MyString();
        assertTrue(testStr.isPalindromic("noon"));
        assertTrue(testStr.isPalindromic("deed"));
        assertTrue(testStr.isPalindromic("level"));
    }

    @Test
    public void testIsEqualStr() {
        MyString testStr = new MyString();
        assertTrue(testStr.isEqualStr("Van Gough is an incredible painter", "van gough is an incredible painter"));
        assertFalse(testStr.isEqualStr(null, null));
    }

    @Test
    public void testSymbolStrTest() {
        MyString testStr = new MyString();
        testStr.symbolStrTest();
    }

    @Test
    public void testFindSubStringIndex() {
        MyString testStr = new MyString();
        assertEquals(testStr.findSubStringIndex("abcabcabc", "ca"), 2);
        assertEquals(testStr.findSubStringIndex("abcabcabc", "da"), -1);
    }

    @Test
    public void testFindSubStringIndexInHalf() {
        MyString testStr = new MyString();
        assertEquals(testStr.findSubStringIndexInHalf("abcabcabc", "ca"), 1);
        assertEquals(testStr.findSubStringIndexInHalf("abcabcabc", "da"), -1);
    }

    @Test
    public void testFindSubStringIndexInRightHalf() {
        MyString testStr = new MyString();
        assertEquals(testStr.findSubStringIndexInRightHalf("abcabcabc", "ca"), 2);
        assertEquals(testStr.findSubStringIndexInRightHalf("abcabcabc", "da"), -1);
    }

    @Test
    public void testStringWithPrefixAndPostFix() {
        MyString testStr = new MyString();
        assertEquals(testStr.stringWithPrefixAndPostFix("ca", new String[]{"cacao", "macao", "aoaoaoa", "caleo", "cat"}, "o"), 2);
    }

    @Test
    public void testStringWithPrefixAndPostFixWithoutSpaces() {
        MyString testStr = new MyString();
        assertEquals(testStr.stringWithPrefixAndPostFixWithoutSpaces("ca", new String[]{" cacao", "ma c ao", "ao aoa oa", "ca l eo", "cat"}, "o"), 2);
    }

    @Test
    public void testReplaceThatStringWith() {
        MyString testStr = new MyString();
        assertEquals(testStr.replaceThatStringWith("Eyjafjallajökull", "ja","la"),"Eylaflallajökull");
    }

    @Test
    public void testStringFromTo() {
        MyString testStr = new MyString();
        assertEquals(testStr.stringFromTo("aloha",1,4), "loh");
    }
}