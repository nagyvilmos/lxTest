/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxTest;

import lexa.test.TestClass;
import lexa.test.TestMethod;

/**
 *
 * @author william
 */
public class TestPass
        extends TestClass
{

    public TestPass()
    {
        super();
    }

    @TestMethod (order = 1)
    public Boolean standardTest()
    {
        return true;
    }
    @TestMethod(order = 0, setUp = "setUpTest", tearDown = "tearDownTest")
    public Boolean specialTest()
    {
        return true;
    }
    public Boolean setUpTest()
    {
        return true;
    }
    public Boolean tearDownTest()
    {
        return true;
    }

    @TestMethod(
            arguments = "testArguments", 
            setUp = "setUpArguments",
            tearDown = "tearDownArguments")
    public Boolean testArguments(Object arg)
    {
        return true;
    }
    public Object[] testArguments()
    {
        return new Object[]{1,2,3};
    }
    public Boolean setUpArguments(Object arg)
    {
        return true;
    }
    public Boolean tearDownArguments(Object arg)
    {
        return true;
    }
}
