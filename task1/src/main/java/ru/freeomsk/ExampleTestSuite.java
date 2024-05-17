package ru.freeomsk;

import ru.freeomsk.annotations.*;

public class ExampleTestSuite {

    @BeforeSuite
    public static void beforeAllTests() {
        System.out.println("Before all tests");
    }

    @BeforeTest
    public static void beforeTest() {
        System.out.println("Before every test");
    }

    @Test(priority = 1)
    public void highPriorityTest() {
        System.out.println("High priority test");
    }

    @Test(priority = 10)
    public void lowPriorityTest() {
        System.out.println("Low priority test");
    }

    @Test
    public void defaultPriorityTest() {
        System.out.println("Default priority test");
    }

    @AfterTest
    public static void afterTest() {
        System.out.println("After every test");
    }

    @AfterSuite
    public static void afterAllTests() {
        System.out.println("After all tests");
    }
}