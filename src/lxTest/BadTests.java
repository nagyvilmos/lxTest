/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxTest;

import lexa.test.TestClass;
import lexa.test.TestException;
import lexa.test.TestMethod;

/**
 *
 * @author william
 */
public class BadTests
        extends TestClass
{

    @Override
    public Boolean setUpClass() throws TestException
    {
        return true;
    }

    @Override
    public Boolean tearDownClass() throws TestException
    {
        return true;
    }
    
    @TestMethod
    public boolean failed() throws TestException
    {
        return false;
    }
    @TestMethod
    public boolean exception() throws TestException
    {
        throw new TestException("That did not work!");
    }
}
