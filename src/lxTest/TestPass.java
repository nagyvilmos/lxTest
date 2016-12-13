/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * TestPass.java
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
import lexa.test.TestMethod;

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
    @TestMethod (order = 1)
    public Boolean standardTest()
    {
        return true;
    }

    /**
     * Test with set up and tear down
     * @return {@code true}
     */
    @TestMethod(order = 2, setUp = "setUpTest", tearDown = "tearDownTest")
    public Boolean specialTest()
    {
        return true;
    }
    
    /**
     * Set up for test
     * @return {@code true}
     */
    public Boolean setUpTest()
    {
        return true;
    }

    /**
     * tear down test
     * @return {@code true}
     */
    public Boolean tearDownTest()
    {
        return true;
    }

    /**
     * Test with arguments
     * @return {@code true}
     */
    @TestMethod(
            order = 99,
            arguments = "testArguments", 
            setUp = "setUpArguments",
            tearDown = "tearDownArguments")
    public Boolean testArguments(Object arg)
    {
        return true;
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
     * @return {@code true}
     */
    public Boolean setUpArguments(Object arg)
    {
        return true;
    }
    
    /**
     * Tear down with arguments
     * @return {@code true}
     */
    public Boolean tearDownArguments(Object arg)
    {
        return true;
    }
}
