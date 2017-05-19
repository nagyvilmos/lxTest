/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * BadTests.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: December 2016
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 *================================================================================
 */
package lxTest;

import lexa.test.TestClass;
import lexa.test.TestAnnotation;
import lexa.test.TestResult;

/**
 * Test that the failure of tests is correctly picked up.
 * @author william
 * @since 2016-12
 */
public class BadTests
        extends TestClass
{

    /**
     * Test that fails
     * @return {@code false}
     */
    @TestAnnotation
    public TestResult failed()
    {
        return new TestResult(false);
    }

    /**
     * Test that fails
     * @return {@code false}
     */
    @TestAnnotation
    public TestResult completeWithException()
    {
        // note it's marked as passed, but will become failed due to exception
        return new TestResult(true, true,
                new Exception("Ths has completed with an exception to say why"));
    }

    /**
     * Test that fails
     * @return {@code false}
     */
    @TestAnnotation
    public TestResult completeWithMessage()
    {
        // note it's marked as passed, but will become failed due to exception
        return new TestResult(false,"Ths has failed with a message to say why");
    }
    /**
     * Test that throws an exception
     * @return  no return is made;
     */
    @TestAnnotation
    public TestResult exception()
    {
        throw new IllegalArgumentException("That did not work!");
    }
}
