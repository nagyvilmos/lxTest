/*
 *==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * TestPass.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: December 2016
 *==============================================================================
 */
package lxTest;

import lexa.test.TestClass;
import lexa.test.TestAnnotation;
import lexa.test.TestResult;

/**
 * Tests that will all pass
 * @author william
 * @since 2016-12
 */
public class TestPass
        extends TestClass
{
    /**
     * Stand alone test
     * @return {@code true}
     */
    @TestAnnotation (order = 1)
    public TestResult standardTest()
    {
        return new TestResult(true);
    }

    /**
     * Test with set up and tear down
     * @return {@code true}
     */
    @TestAnnotation(order = 2, setUp = "setUpTest", tearDown = "tearDownTest")
    public TestResult specialTest()
    {
        return new TestResult(true);
    }

    /**
     * Set up for test
     * @return {@code true}
     */
    public TestResult setUpTest()
    {
        return new TestResult(true);
    }

    /**
     * tear down test
     * @return {@code true}
     */
    public TestResult tearDownTest()
    {
        return new TestResult(true);
    }

    /**
     * Test with arguments
     * @param arg step argument
     * @return {@code true}
     */
    @TestAnnotation(
            order = 99,
            arguments = "testArguments",
            setUp = "setUpArguments",
            tearDown = "tearDownArguments")
    public TestResult testArguments(Object arg)
    {
        return new TestResult(true);
    }
    /**
     * Arguments for test
     * @return an array of numbers
     */
    public Object[] testArguments()
    {
        return new Object[]{1,2,3};
    }

    /**
     * Set up with arguments
     * @param arg step argument
     * @return {@code true}
     */
    public TestResult setUpArguments(Object arg)
    {
        return new TestResult(true);
    }

    /**
     * Tear down with arguments
     * @param arg step argument
     * @return {@code true}
     */
    public TestResult tearDownArguments(Object arg)
    {
        return new TestResult(true);
    }
}
