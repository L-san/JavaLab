package ru.ssau.tk.lsan.task4;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ExceptionsTasksTest {

    @Test
    public void testIntegerDivision() {
        String int1 = "1";
        String int100 = "100";
        String int0 = "0";

        assertEquals(ExceptionsTasks.integerDivision(int100,int1),100);
        assertThrows(ArithmeticException.class, ()->{ExceptionsTasks.integerDivision(int100,int0);});
        assertThrows(NumberFormatException.class, ()->{ExceptionsTasks.integerDivision(null,null);});
    }
}