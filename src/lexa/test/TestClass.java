/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * TestClass.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: December 2016
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 *================================================================================
 */
package lexa.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract super class for test cases
 * This class is extended for a test case.
 * Each test method must use the annotation {@link TestAnnotation @TestAnnotation}.
 * If there are any class level methods for arguments, set up or tear down then
 * use the annotation on the class definition.
 * 
 * @author william
 * @since 2016-12
 */
public abstract class TestClass
        implements TestClassInterface
{
    /** Tests to be performed */
    private final List<Test> tests = new ArrayList();
    /** Class level arguments */
    private Object[] arguments;
    /** Method to return class arguments */
    private Method classArgumentsMethod;
    /** Method to set up the class before testing */
    private Method setUpClassMethod;
    /** Method to tear down the class after testing */
    private Method tearDownClassMethod;

    @Override
    public TestResult execute(boolean stopOnError)
    {
        TestResult result = new TestResult(this.getClass().getCanonicalName());
        
        if (result.addResultIfFailed(this.loadTestClass()))
        {
            return result;
        }

        boolean useArgument = (this.classArgumentsMethod != null);

        if (result.addResultIfFailed(this.classArgumentsResult()))
        {
            return result;
        }

        for (Object arg : this.arguments)
        {
            if (result.addResultIfFailed(
                    this.setUpClassResult(useArgument, arg)) && stopOnError)
            {
                return result;
            }
            for (Test test : this.tests)
            {
                TestResult tr = this.executeTest(test, stopOnError,useArgument, arg);
                result.addResult(tr);
                if (stopOnError && !tr.passed())
                {
                    return result;
                }
            }
            if (result.addResultIfFailed(this.tearDownClassResult(useArgument, arg)) && stopOnError)
            {
                return result;
            }
        }

        return result;
    }

    private TestResult executeTest(Test test, boolean stopOnError,
            boolean useClassArgument, Object classArgument)
    {
        TestResult result =
                this.testArgumentsResult(test, useClassArgument, classArgument);
        if (result == null)
        {
            if (!useClassArgument && test.argumentsMethod == null)
            {
                result = this.executeTestStep(test, false, 0, stopOnError);
            }
            else
            {
                result = new TestResult(this.getClass().getSimpleName() + '.' +
                        test.testMethod.getName());
                for (int step = 0;
                        step < test.arguments.length;
                        step ++)
                {
                    TestResult tr = this.executeTestStep(test, true, step, stopOnError);
                    result.addResult(tr);
                    if (stopOnError && !tr.passed())
                    {
                        break;
                    }
                }
            }
        }
        return result;
    }

    private TestResult loadTestClass()
    {
        String resultName = this.getClass().getSimpleName() + ".loadTestClass";
        try {
            if (this.tests.isEmpty())
            {
                Class testClass = this.getClass();
                if (testClass.isAnnotationPresent(TestAnnotation.class))
                {
                    TestAnnotation classAnnotation =
                            (TestAnnotation) testClass.getAnnotation(TestAnnotation.class);
                    if (classAnnotation.order() != Integer.MAX_VALUE)
                    {
                        throw new IllegalArgumentException("Class annotation may not define order");
                    }
                    if (classAnnotation.arguments().isEmpty())
                    {
                        this.classArgumentsMethod = null;
                        this.setUpClassMethod = classAnnotation.setUp().isEmpty() ? null :
                                testClass.getMethod(classAnnotation.setUp());
                        this.tearDownClassMethod = classAnnotation.tearDown().isEmpty() ? null :
                                testClass.getMethod(classAnnotation.tearDown());
                    }
                    else
                    {
                        this.classArgumentsMethod = testClass.getMethod(classAnnotation.arguments());
                        this.setUpClassMethod = classAnnotation.setUp().isEmpty() ? null :
                                testClass.getMethod(classAnnotation.setUp(),
                                        Object.class);
                        this.tearDownClassMethod = classAnnotation.tearDown().isEmpty() ? null :
                                testClass.getMethod(classAnnotation.tearDown(),
                                        Object.class);
                    }
                }
                for (Method method : testClass.getMethods())
                {
                    if (method.isAnnotationPresent(TestAnnotation.class))
                    {
                        TestAnnotation testAnnotation = method.getAnnotation(TestAnnotation.class);
                        Test test = new Test();
                        test.testMethod = method;
                        test.order = testAnnotation.order();
                        if (testAnnotation.arguments().isEmpty() &&
                                this.classArgumentsMethod == null)
                        {
                            test.argumentsMethod = null;
                            test.setUpMethod = testAnnotation.setUp().isEmpty() ? null :
                                    testClass.getMethod(testAnnotation.setUp());
                            test.tearDownMethod = testAnnotation.tearDown().isEmpty() ? null :
                                    testClass.getMethod(testAnnotation.tearDown());
                        }
                        else
                        {
                            if (this.classArgumentsMethod != null)
                            {
                                test.argumentsMethod = testAnnotation.arguments().isEmpty() ? null :
                                        testClass.getMethod(testAnnotation.arguments(), Object.class);
                            }
                            else
                            {
                                test.argumentsMethod = testClass.getMethod(testAnnotation.arguments());
                            }
                            test.setUpMethod = testAnnotation.setUp().isEmpty() ? null :
                                    testClass.getMethod(testAnnotation.setUp(),
                                            Object.class);
                            test.tearDownMethod = testAnnotation.tearDown().isEmpty() ? null :
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
        }
        catch (Exception ex)
        {
            return new TestResult(resultName,false,false,ex);
        }
        return new TestResult(resultName,true,true,null);        
    }
    private TestResult setUpClassResult(boolean useArg, Object arg)
    {
        String resultName = this.getClass().getSimpleName() + ".setUpClass";
        if (useArg)
        {
            resultName = resultName + '(' + arg + ')';
        }
        try
        {
            if (this.setUpClassMethod != null)
            {
                return methodCallResult(this.setUpClassMethod, useArg, arg);
            }
        }
        catch (Exception ex)
        {
            return new TestResult(resultName,false,false,ex);
        }
        return new TestResult(resultName,true,true,null);
    }

    private TestResult classArgumentsResult()
    {
        String resultName = this.getClass().getSimpleName() + ".classArguments";
        if (this.classArgumentsMethod == null)
        {
            this.arguments = new Object[]{null};
        }
        else
        {
            try {
                this.arguments = (Object[])this.classArgumentsMethod.invoke(this);
            }
            catch (Exception ex)
            {
                return new TestResult(resultName,false,false,ex);
            }
        }
        return new TestResult(resultName,true,true,null);
    }

    private TestResult testArgumentsResult(Test test, boolean useClassArgument, Object classArgument)
    {
        if (test.argumentsMethod == null)
        {
            test.arguments = new Object[]{classArgument};
        }
        else
        {
            try {
                if (useClassArgument)
                {
                    test.arguments = (Object[])test.argumentsMethod.invoke(this, classArgument);
                }
                else
                {
                    test.arguments = (Object[])test.argumentsMethod.invoke(this);
                }
            }
            catch (Exception ex)
            {
                return new TestResult(test.argumentsMethod.getName(),false,false,ex);
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
        String methodName = 
                this.getClass().getSimpleName() + '.' +
                (useArgument ?
                (method.getName() + '(' + argument + ')') :
                method.getName());
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
    
    private TestResult tearDownClassResult(boolean useArg, Object arg)
    {
        String resultName = this.getClass().getSimpleName() + ".tearDownClass";
        if (useArg)
        {
            resultName = resultName + '(' + arg + ')';
        }
        try
        {
            if (this.tearDownClassMethod != null)
            {
                return methodCallResult(this.tearDownClassMethod, useArg, arg);
            }
        }
        catch (Exception ex)
        {
            return new TestResult(resultName,false,false,ex);
        }
        return new TestResult(resultName,true,true,null);
    }

    private TestResult executeTestStep(Test test, boolean useArg, int step, boolean stopOnError)
    {
        Object arg = useArg ? test.arguments[step] : null;
        TestResult setUp = methodCallResult(test.setUpMethod, useArg, arg);
        if (!setUp.passed())
        {
            return setUp;
        }
        TestResult result = methodCallResult(test.testMethod, useArg, arg);
        if (result.passed())
        {
            TestResult tearDown = methodCallResult(test.tearDownMethod, useArg, arg);
            if (!tearDown.passed())
            {
                return tearDown;
            }
        }
        return result;
    }

    private class Test
    {
        int order;
        Method setUpMethod;
        Method testMethod;
        Method tearDownMethod;
        Method argumentsMethod;
        Object[] arguments; // populated list of arguments from the method;
    }
}
