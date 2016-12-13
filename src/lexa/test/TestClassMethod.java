/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexa.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author william
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestClassMethod
{
    String arguments() default "";
    String setUp() default "";
    String tearDown() default "";
}
