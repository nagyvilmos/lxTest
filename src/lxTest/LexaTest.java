/*
 *==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * LexaTest.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: December 2016
 *==============================================================================
 */
package lxTest;

import lexa.test.TestClassInterface;
import lexa.test.TestRun;

/**
 * Test handler to make sure the library works
 * @author william
 * @since 2016-12
 */
public class LexaTest
{
    /**
     * Main routine, fires off all the test cases.
     * @param args not used.
     */
    public static void main (String ... args)
    {
        System.out.println(
            new TestRun(
                new TestClassInterface[]
                {
                    new TestPass(),
                    new ClassMethodTests(),
                    new BadTests(),
                    new InterfaceTest()
                })
            .execute()
            .getReport(true,true)
        );
    }
}
