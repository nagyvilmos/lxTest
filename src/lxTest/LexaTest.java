/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxTest;

import lexa.test.TestClass;
import lexa.test.TestRun;

/**
 *
 * @author william
 */
public class LexaTest
{
    public static void main (String ... args)
    {
        System.out.println(
            new TestRun(
                new TestClass[]{new TestPass(), new BadTests()})
            .execute()
            .getReport(true,true)
        );
    }
}
