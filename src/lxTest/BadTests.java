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
                new Exception("This has completed with an exception to say why"));
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
                "This has failed with a message to say why");
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


    /**
     * Test that fails
     * @return {@code false}
     */
    @TestAnnotation (order = 5)
    public TestResult nullTest()
    {
        return TestResult.isNull("");
    }

     /**
     * Test that fails
     * @return {@code false}
     */
   @TestAnnotation (order = 6)
    public TestResult notNullTest()
    {
        return TestResult.notNull(null);
    }

    /**
     * Test that fails
     * @return {@code false}
     */
    @TestAnnotation (order = 7)
    public TestResult all()
    {
        return TestResult.all (
                TestResult.result(true),
                this.notNullTest()
        );
    }

    /**
     * Test that fails
     * @return {@code false}
     */
    @TestAnnotation (order = 8)
    public TestResult rangeBelow()
    {
        return TestResult.result (1.1, 1.2, 1.0);
    }

    /**
     * Test that fails
     * @return {@code false}
     */
    @TestAnnotation (order = 9)
    public TestResult rangeAbove()
    {
        return TestResult.result (1.1, 1.2, 1.3);
    }

    /**
     * Test that fails
     * @return {@code false}
     */
    @TestAnnotation (order = 10)
    public TestResult isClass()
    {
        return TestResult.isClass("String", 7);
    }

    /**
     * Test that fails
     * @return {@code false}
     */
    @TestAnnotation (order = 11)
    public TestResult isClassNull()
    {
        return TestResult.isClass("String", null);
    }

    /**
     * Test that fails
     * @return {@code false}
     */
    @TestAnnotation (order = 20)
    public TestResult assignableTo()
    {
        return TestResult.assignableTo("java.lang.String", 17);
    }

    /**
     * Test that fails
     * @return {@code false}
     */
    @TestAnnotation (order = 22)
    public TestResult assignableToNull()
    {
        return TestResult.assignableTo("java.lang.Object", null);
    }
}
