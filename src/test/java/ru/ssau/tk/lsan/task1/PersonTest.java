package ru.ssau.tk.lsan.task1;

import org.testng.annotations.Test;

public class PersonTest {
    Person testPersonNull = new Person(null,null,0);
    Person testPersonUnInitialazed = new Person();
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
}