package ru.freeomsk.task1;

import ru.freeomsk.task1.annotations.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestRunner {

    public static void runTests(Class<?> c) {
        int beforeSuiteCount = 0;
        int afterSuiteCount = 0;
        Method beforeSuite = null;
        Method afterSuite = null;
        List<Method> beforeTestMethods = new ArrayList<>();
        List<Method> afterTestMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();

        for (Method method : c.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                beforeSuiteCount++;
                if (beforeSuiteCount > 1) {
                    throw new RuntimeException("Only one method can be annotated with @BeforeSuite");
                }
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException("@BeforeSuite method must be static");
                }
                beforeSuite = method;
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                afterSuiteCount++;
                if (afterSuiteCount > 1) {
                    throw new RuntimeException("Only one method can be annotated with @AfterSuite");
                }
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException("@AfterSuite method must be static");
                }
                afterSuite = method;
            } else if (method.isAnnotationPresent(BeforeTest.class)) {
                beforeTestMethods.add(method);
            } else if (method.isAnnotationPresent(AfterTest.class)) {
                afterTestMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }

        testMethods.sort(Comparator.comparingInt(m -> m.getAnnotation(Test.class).priority()));

        try {
            Object testInstance = c.getDeclaredConstructor().newInstance();

            if (beforeSuite != null) {
                beforeSuite.invoke(null);
            }

            for (Method testMethod : testMethods) {
                for (Method beforeTestMethod : beforeTestMethods) {
                    beforeTestMethod.invoke(testInstance);
                }

                testMethod.invoke(testInstance);

                for (Method afterTestMethod : afterTestMethods) {
                    afterTestMethod.invoke(testInstance);
                }
            }

            if (afterSuite != null) {
                afterSuite.invoke(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}