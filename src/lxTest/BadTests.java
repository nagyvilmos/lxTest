/*
 *==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * BadTests.java
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
    @TestAnnotation (order = 1)
    public TestResult failed()
    {
        return TestResult.result(false);
    }

    /**
     * Test that fails
     * @return {@code false}
     */
    @TestAnnotation (order = 2)
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
    @TestAnnotation (order = 3)
    public TestResult completeWithMessage()
    {
        // note it's marked as passed, but will become failed due to exception
        return TestResult.result(true, false,
                "Ths has failed with a message to say why");
    }
    /**
     * Test that throws an exception
     * @return  no return is made;
     */
    @TestAnnotation (order = 4)
    public TestResult exception()
    {
        throw new IllegalArgumentException("That did not work!");
    }


    @TestAnnotation (order = 5)
    public TestResult nullTest()
    {
        return TestResult.isNull("");
    }

    @TestAnnotation (order = 6)
    public TestResult notNullTest()
    {
        return TestResult.notNull(null);
    }

}
