/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxTest;

import lexa.test.TestClass;
import lexa.test.TestClassMethod;
import lexa.test.TestMethod;

/**
 *
 * @author william
 */
@TestClassMethod(arguments = "classArguments", setUp = "classSetUp", tearDown = "classTearDown")
public class ClassMethodTests
        extends TestClass
{
    public Object[] classArguments()
    {
        return new Object[]{"this", "that", "other"};
    }
    public boolean classSetUp(Object arg)
    {
        return true;
    }
    public boolean classTearDown(Object arg)
    {
        return true;
    }

    @TestMethod(setUp = "testSetUp", tearDown = "testTearDown")
    public boolean test(Object arg)
    {
        return true;
    }
    public boolean testSetUp(Object arg)
    {
        return true;
    }
    public boolean testTearDown(Object arg)
    {
        return true;
    }


    @TestMethod(arguments = "doubleArguments")
    public boolean arguments(Object arg)
    {
        return true;
    }
    public Object[] doubleArguments(Object arg)
    {
        return new Object[]{arg + ".this", arg + ".that", arg + ".other"};
    }
}
