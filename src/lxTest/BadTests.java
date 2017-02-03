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
    public boolean failed()
    {
        return false;
    }

    /**
     * Test that throws an exception
     * @return  no return is made;
     */
    @TestAnnotation
    public boolean exception()
    {
        throw new IllegalArgumentException("That did not work!");
    }
}
