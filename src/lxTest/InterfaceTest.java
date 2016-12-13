/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * InterfaceTest.java
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

import lexa.test.TestClassInterface;
import lexa.test.TestResult;

/**
 * Test implementation for an interface 
 * @author william
 * @since 2016-12
 */
public class InterfaceTest
        implements TestClassInterface
{

    @Override
    public TestResult execute(boolean stopOnError)
    {
        return new TestResult("InterfaceTest", true);
    }
    
}
