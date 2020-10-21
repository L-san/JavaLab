package ru.ssau.tk.lsan.task1;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class NamedPointTest {

    NamedPoint origin() {
        return new NamedPoint();
    }

    NamedPoint named() {
        return new NamedPoint(1, 2, 3, "name");
    }

    NamedPoint noName() {
        return new NamedPoint(1, 2, 3);
    }

    @Test
    public void testGetName() {
        assertEquals(origin().getName(), "Origin");
        assertEquals(named().getName(), "name");
    }

    @Test
    public void testSetName() {
        NamedPoint testOrigin = origin();
        NamedPoint testNamed = named();

        testOrigin.setName("good");
        testNamed.setName("luck");

        assertEquals(testOrigin.getName(), "good");
        assertEquals( testNamed.getName(), "luck");
    }
}