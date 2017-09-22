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
     */
    String arguments() default "";

    /**
     * Name of method to perform set up for the test.
     * <br>
     * When present the argument gives the name of a method that must return
     * a {@link TestResult}.
     * If there are no arguments:
     * {@code public TestResult method()}
     * Otherwise:
     * {@code public TestResult method(Object arg)}
     *
     * @return the name of the set up method.
     */
    String setUp() default "";

    /**
     * Name of method to perform tear down for the test.
     * <br>
     * When present the argument gives the name of a method that must return
     * a {@link TestResult}.
     * If there are no arguments:
     * {@code public TestResult method()}
     * Otherwise:
     * {@code public TestResult method(Object arg)}
     *
     * @return the name of the tear down method.
     */
    String tearDown() default "";

    /**
     * The order in which the test is to performed.
     * If this argument is omitted then there is no defined order. In a test
     * class with a mixture of tests with and without an order, the ordered
     * tests will be performed first.
     * Setting at the class, this will cause an {@link IllegalArgumentException}
     * to be thrown.
     * @return the order in which the test will be performed
     */
    int order() default Integer.MAX_VALUE;
}
