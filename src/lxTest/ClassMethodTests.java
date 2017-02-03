/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ClassMethodTests.java
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
    public boolean classSetUp(Object arg)
    {
        return true;
    }

    /**
     * Class tear down
     * @param arg the current test
     * @return {@code true}
     */
    public boolean classTearDown(Object arg)
    {
        return true;
    }

    /**
     * Test with arguments
     * @param arg the current test
     * @return {@code true}
     */
    @TestAnnotation(setUp = "testSetUp", tearDown = "testTearDown")
    public boolean test(Object arg)
    {
        return true;
    }

    /**
     * Test set up with arguments
     * @param arg the current test
     * @return {@code true}
     */
    public boolean testSetUp(Object arg)
    {
        return true;
    }

    /**
     * Test tear down with arguments
     * @param arg the current test
     * @return {@code true}
     */
    public boolean testTearDown(Object arg)
    {
        return true;
    }


    /**
     * Test with class and test arguments
     * @param arg the current test
     * @return {@code true}
     */
    @TestAnnotation(arguments = "doubleArguments")
    public boolean arguments(Object arg)
    {
        return true;
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
