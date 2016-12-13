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
public class BadTests
        extends TestClass
{

    @TestMethod
    public boolean failed()
    {
        return false;
    }
    @TestMethod
    public boolean exception()
    {
        throw new IllegalArgumentException("That did not work!");
    }
}
