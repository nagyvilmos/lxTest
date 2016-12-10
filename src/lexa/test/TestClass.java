/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexa.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author william
 */
public abstract class TestClass
{
    private final List<Test> tests;
    
    public TestClass()
    {
        // set up all the reflection for the class:
        this.tests = new ArrayList();
    }
    public abstract Boolean setUpClass()
            throws TestException;
    public abstract Boolean tearDownClass()
            throws TestException;
    
    public TestResult execute()
    {
        TestResult result = new TestResult(this.getClass().getCanonicalName());
        
        TestResult setUp = this.setUpClassResult();
        if (setUp != null)
        {
            result.addResult(setUp);
            return result;
        }
        
        for (Test test : this.tests)
        {
            result.addResult(this.executeTest(test));
        }
        
        TestResult tearDown = this.tearDownClassResult();
        if (tearDown != null)
        {
            result.addResult(tearDown);
            return result;
        }

        return result;
    }

    private TestResult executeTest(Test test)
    {
        TestResult result = this.argumentResult(test);
        if (result == null)
        {
            if (test.arguments == null)
            {
                result = this.executeTestStep(test, false, 0);
            }
            else
            {
                result = new TestResult(test.test.getName());
                for (int step = 0;
                        step < test.args.length;
                        step ++)
                {
                    result.addResult(
                        this.executeTestStep(test, true, step));
                }
            }
        }
        return result;
            
    }

    private TestResult setUpClassResult()
    {
        try {
            if (this.tests.isEmpty())
            {
                Class testClass = this.getClass();
                for (Method method : testClass.getMethods())
                {
                    if (method.isAnnotationPresent(TestMethod.class))
                    {
                        TestMethod testAnnotation = method.getAnnotation(TestMethod.class);
                        Test test = new Test();
                        test.test = method;
                        test.order = testAnnotation.order();
                        if (testAnnotation.arguments().isEmpty())
                        {
                            test.arguments = null;
                            test.setUp = testAnnotation.setUp().isEmpty() ? null :
                                    testClass.getMethod(testAnnotation.setUp());
                            test.tearDown = testAnnotation.tearDown().isEmpty() ? null :
                                    testClass.getMethod(testAnnotation.tearDown());
                        }
                        else
                        {
                            test.arguments = testClass.getMethod(testAnnotation.arguments());
                            test.setUp = testAnnotation.setUp().isEmpty() ? null :
                                    testClass.getMethod(testAnnotation.setUp(),
                                            Object.class);
                            test.tearDown = testAnnotation.tearDown().isEmpty() ? null :
                                    testClass.getMethod(testAnnotation.tearDown(),
                                            Object.class);
                        }
                        int pos;
                        for (pos = 0;
                                pos < this.tests.size();
                                pos++)
                        {
                            if (test.order < this.tests.get(pos).order)
                            {
                                break;
                            }
                        }
                        this.tests.add(pos, test);
                    }
                }
            }
            
            if (!this.setUpClass())
            {
                return new TestResult(this.getClass().getSimpleName() + ".setUpClass"
                        ,true,false,null);
            }
        }
        catch (Exception ex)
        {
            return new TestResult(this.getClass().getSimpleName() + ".setUpClass"
                    ,false,false,ex);
        }
        return null; // no result needed
    }

    private TestResult argumentResult(Test test)
    {
        if (test.arguments == null)
        {
            test.args = new Object[]{null};
        }
        else
        {
            try {
                test.args = (Object[])test.arguments.invoke(this);
            }
            catch (Exception ex)
            {
                return new TestResult(test.arguments.getName(),false,false,ex);
            }
        }
        return null;
    }

    private TestResult methodCallResult(Method method, boolean useArgument, Object argument)
    {
        if (method == null)
        {
            return new TestResult("[null method]",true,true,null);
        }
        String methodName = useArgument ?
                (method.getName() + '(' + argument + ')') :
                method.getName();
        try {
            boolean result = (Boolean)(useArgument ?
                    method.invoke(this, argument) :
                    method.invoke(this));
            return new TestResult(methodName, true,result,null);
        }
        catch (InvocationTargetException ex)
        {
            return new TestResult(methodName,false,false,ex.getCause());
        }
        catch (Exception ex) // anything else
        {
            return new TestResult(methodName,false,false,ex);
        }
    }
    
    private TestResult tearDownClassResult()
    {
        try {
            if (!this.tearDownClass())
            {
                return new TestResult(this.getClass().getSimpleName() + ".tearDownClass"
                        ,true,false,null);
            }
        }
        catch (Exception ex)
        {
            return new TestResult(this.getClass().getSimpleName() + ".tearDownClass"
                    ,false,false,ex);
        }
        return null; // no result needed
    }

    private TestResult executeTestStep(Test test, boolean useArg, int step)
    {
        Object arg = useArg ? test.args[step] : null;
        TestResult setUp = methodCallResult(test.setUp, useArg, arg);
        if (!setUp.passed())
        {
            return setUp;
        }
        TestResult result = methodCallResult(test.test, useArg, arg);
        if (result.passed())
        {
            TestResult tearDown = methodCallResult(test.tearDown, useArg, arg);
            if (!tearDown.passed())
            {
                return tearDown;
            }
        }
        return result;
    }
    
//    public TestResult execute(String testMethod)
//    {
//        
//    }
//
//    public TestResult execute(String testMethod, Object argument)
//    {
//        
//    }
    
    private class Test
    {
        int order;
        Method setUp;
        Method test;
        Method tearDown;
        Method arguments;
        Object[] args; // populated list of arguments from the method;
    }
}
