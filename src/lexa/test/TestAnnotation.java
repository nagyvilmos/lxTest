/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexa.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Annotation for test classes and methods.
 * <br>
 * This allows a test class to be defined with set up and tear down methods, and
 * a list of arguments to run a loop of tests.
 * This is also used to indicate that the annotated method is a test.
 * For a method the signature must be either:
 * If there are no arguments:
 * {@code public Boolean method()}
 * Otherwise:
 * {@code public Boolean method(Object arg)}
 * 
 * @author william
 * @since 2016-12
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation
{

    /**
     * Name of method to supply the list of method arguments for the test.
     * <br>
     * When present the argument gives the name of a method that must return 
     * an array of argument to use by the test.
     * If there are no class arguments:
     * {@code public Object[] method()}
     * Otherwise:
     * {@code public Object[] method(Object arg)}
     * 
     * @return the name of the arguments method.
     */    String arguments() default "";

    /**
     *
     * @return
     */
    String setUp() default "";

    /**
     *
     * @return
     */
    String tearDown() default "";

    /**
     *
     * Setting at the class, this will cause an {@link IllegalArgumentException}
     * to be thrown.
     * @return
     */
    int order() default Integer.MAX_VALUE;
}
