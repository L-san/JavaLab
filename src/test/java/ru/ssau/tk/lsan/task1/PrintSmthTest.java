package ru.ssau.tk.lsan.task1;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PrintSmthTest extends PrintSmth{

    @Test
    public void testPrintType() {
        byte numByte = 0xF;
        char numChar = 'v';
        short numShort = 5;
        int numInt = 6;
        long numLong= 7;
        float numFloat= 8.1f;
        double numDouble = 9.1;
        boolean numBoolean = true;
        Object obj = new Object();
        Integer objInt = 2;
        printType(numByte);
        printType(numChar);
        printType(numShort);
        printType(numInt);
        printType(numLong);
        printType(numFloat);
        printType(numDouble);
        printType(numBoolean);
        printType(obj);
        printType(objInt);
        printType(null);
    }
}