package ru.ssau.tk.lsan.task1;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class PersonTest {
    Person testPersonNull = new Person(null,null,0);
    Person testPersonUnInitialazed = new Person();
    Person testSmb = new Person("Somebody", "OnceToldMe",2);
    @Test
    public void testGetFirstName() {
        testPersonNull.getFirstName();
        testPersonUnInitialazed.getFirstName();
    }

    @Test
    public void testGetLastName() {
        testPersonNull.getLastName();
        testPersonUnInitialazed.getLastName();
    }

    @Test
    public void testGetPassportId() {
        testPersonNull.getPassportId();
        testPersonUnInitialazed.getPassportId();
    }

    @Test
    public void testSetLastName() {
    }


    @Test
    public void testSetFirstName() {
    }

    @Test
    public void testSetPassportId() {
    }

    @Test
    public void testTestToString() {
        assertEquals(testSmb.toString(),"Somebody OnceToldMe");
    }
}