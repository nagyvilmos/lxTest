/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxTest;

import lexa.test.TestClassInterface;
import lexa.test.TestResult;

/**
 *
 * @author william
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
