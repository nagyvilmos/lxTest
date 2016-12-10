/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexa.test;

/**
 *
 * @author william
 */
public class TestException extends Exception
{
    public TestException()
    {
        super("Test Exception");
    }
    public TestException(String message)
    {
        super(message);
    }
    public TestException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
