/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ClassMethodTests.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: December 2016
 *================================================================================
 */
package lxTest;

import lexa.test.TestClass;
import lexa.test.TestAnnotation;
import lexa.test.TestResult;

/**
 * Test the class methods are okay
 * @author william
 * @since 2016-12
 */
@TestAnnotation(arguments = "classArguments", setUp = "classSetUp", tearDown = "classTearDown")
public class ClassMethodTests
        extends TestClass
{
    /**
     * Class arguments used for all tests
     * @return an array of strings
     */
    public Object[] classArguments()
    {
        return new Object[]{"this", "that", "other"};
    }

    /**
     * Class set up
     * @param arg the current test
     * @return {@code true}
     */
    public TestResult classSetUp(Object arg)
    {
        return TestResult.result(true);
    }

    /**
     * Class tear down
     * @param arg the current test
     * @return {@code true}
     */
    public TestResult classTearDown(Object arg)
    {
        return TestResult.result(true);
    }

    /**
     * Test with arguments
     * @param arg the current test
     * @return {@code true}
     */
    @TestAnnotation(setUp = "testSetUp", tearDown = "testTearDown")
    public TestResult test(Object arg)
    {
        return TestResult.result(true);
    }

    /**
     * Test set up with arguments
     * @param arg the current test
     * @return {@code true}
     */
    public TestResult testSetUp(Object arg)
    {
        return TestResult.result(true);
    }

    /**
     * Test tear down with arguments
     * @param arg the current test
     * @return {@code true}
     */
    public TestResult testTearDown(Object arg)
    {
        return TestResult.result(true);
    }


    /**
     * Test with class and test arguments
     * @param arg the current test
     * @return {@code true}
     */
    @TestAnnotation(arguments = "doubleArguments")
    public TestResult arguments(Object arg)
    {
        return TestResult.result(true);
    }

    /**
     * Test with class and test arguments
     * @param arg the current class test
     * @return an array combining the current test and these
     */
    public Object[] doubleArguments(Object arg)
    {
        return new Object[]{arg + ".this", arg + ".that", arg + ".other"};
    }
}
