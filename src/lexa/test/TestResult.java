/*
 *==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * TestREsult.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: December 2016
 *==============================================================================
 */
package lexa.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide the results from a test.
 * The results can be from a single test or from a group of tests making up a
 * run or part of a run.
 *
 * @author william
 * @since 2016-12
 */
public class TestResult
{
    private final String message;
    private final String name;
    private final long elapsed;
    private final Boolean complete;
    private final Boolean pass;
    private final Throwable exception;
    private final List<TestResult> children;

    /**
     * Add elapsed time to test results
     * <p>Create a copy of the results with the added elapsed time.
     * @param   result
     *          the results to add elapsed time to.
     * @param   elapsed
     *          number of milliseconds to assign to the test.
     */
    public TestResult(TestResult result, long elapsed)
    {
        this.name = result.name;
        this.elapsed = elapsed;
        this.complete = result.complete;
        this.pass = result.pass;
        this.exception = result.exception;
        this.children = result.children;
        this.message = result.message;
    }
    /**
     * Create a parent result set
     * @param name
     *          The name for the results
     */
    public TestResult(String name)
    {
        this.name = name;
        this.elapsed = 0;
        this.complete = null;
        this.pass = null;
        this.exception = null;
        this.message = null;
        this.children = new ArrayList();
    }

    /**
     * Create a test result by replacing the name.
     * @param name
     *          The name for the results
     * @param rename
     *          The results being renamed
     */
    public TestResult(String name, TestResult rename)
    {
        this.name = name;
        this.elapsed = rename.elapsed;
        this.complete = rename.complete;
        this.pass = rename.pass;
        this.exception = rename.exception;
        this.children = rename.children;
        this.message = rename.message;
    }

    /**
     * Create an unnamed result for a single test.
     *
     * @param pass
     *          Indicate if the result is a pass or fail
     */
    public TestResult(boolean pass)
    {
        this(pass, null);
    }

    /**
     * Create an unnamed result for a single test.
     *
     * @param pass
     *          Indicate if the result is a pass or fail
     * @param message
     *          A message to accompany the test
     */
    public TestResult(boolean pass, String message)
    {
        this(null, true, pass, null, message);
    }

    /**
     * Create a result for a single test.
     *
     * @param name
     *          The name for the results
     * @param pass
     *          Indicate if the result is a pass or fail
     */
    public TestResult(String name, boolean pass)
    {
        this(name, pass, null);
    }

    /**
     * Create a result for a single test.
     *
     * @param name
     *          The name for the results
     * @param pass
     *          Indicate if the result is a pass or fail
     * @param message
     *          A message to accompany the test
     */
    public TestResult(String name, boolean pass, String message)
    {
        this(name, true, pass, null, message);
    }

    /**
     * Create an unnamed result for a single test.
     *
     * @param complete
     *          Indicate if the test actually completed
     * @param pass
     *          Indicate if the result is a pass or fail
     * @param exception
     *          An exception caught by the test
     */
    public TestResult(boolean complete, boolean pass, Throwable exception)
    {
        this(null,complete, pass, exception);
    }

    /**
     * Create a result for a single test.
     *
     * @param name
     *          The name for the results
     * @param complete
     *          Indicate if the test actually completed
     * @param pass
     *          Indicate if the result is a pass or fail
     * @param exception
     *          An exception caught by the test
     */
    public TestResult(String name, boolean complete, boolean pass, Throwable exception)
    {
        this(name, complete, pass, exception, null);
    }

    /**
     * Create a result for a single test.
     *
     * @param name
     *          The name for the results
     * @param complete
     *          Indicate if the test actually completed
     * @param pass
     *          Indicate if the result is a pass or fail
     * @param exception
     *          An exception caught by the test
     * @param message
     *          A message to accompany the test
     */
    public TestResult(String name, boolean complete, boolean pass, Throwable exception, String message)
    {

        this.name = name;
        // The three items MUST be consistant:
        this.pass = pass && complete && (exception == null);
        this.complete = complete && (exception == null);
        this.elapsed = 0;
        this.exception = exception;
        this.children = null;
        this.message = this.pass ? null : message;
    }

    /**
     * Check if the results are a parent based on holding child results
     *
     * @return  {@code true} if the results are a parent,
     *          otherwise {@code false}.
     */
    public boolean isParent()
    {
        return (this.children != null);
    }

    /**
     * Add a result to a parent result set
     * <p>
     * If the result passed is a parent with only a single child, then the child
     * is added, ignoring the loan parent.
     *
     * @param result
     *          the result to add
     * @throws IllegalArgumentException
     *          when trying to add results to non-parent.
     */
    public void addResult(TestResult result)
    {
        if (!this.isParent())
            throw new IllegalArgumentException("Can only add children to a parent");
        if (result.isParent() && result.children.size() == 1)
        {
            // ignore parent container with a single test
            this.addResult(result.children.get(0));
            return;
        }
        this.children.add(result);
    }

    /**
     * Add a result to this result if it has not passed.
     * <p>
     * @param result
     *          the results to add if they did not pass.
     * @return  {@code true} if the result was added,
     *          otherwise {@code false}.
     */
    public boolean addResultIfFailed(TestResult result)
    {
        if (!result.passed())
        {
            this.addResult(result);
            return true;
        }
        return false;
    }

    /**
     * Get the name assigned to the results
     * @return  the name assigned to the results
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Get the elapsed time for the test
     *
     * <p>Get the number of milliseconds elapsed for the test.  If this is a
     * parent result then the time is the sum of all the children's elapsed
     * time.
     *
     * @return  the elapsed time for the test
     */
    public long getElapsedTime()
    {
        if (!this.isParent())
            return this.elapsed;

        long elapsedTime = 0;
        for (TestResult result : this.children)
            elapsedTime+= result.getElapsedTime();
        return elapsedTime;
    }

    /**
     * Get the exception assigned to the results
     * @return  the exception assigned to the results
     */
    public Throwable getException()
    {
        return this.exception;
    }

    /**
     * Get the total number of completed tests.
     * If this is a parent then the sum of all the children's completed tests.
     * If this is a child this returns 1 for a completed test otherwise 0.
     * @return  the total number of completed tests
     */
    public int getCompleteCount()
    {
        if (!this.isParent())
            return this.complete ? 1 : 0;
        int count=0;
        for (TestResult result : this.children)
            count+= result.getCompleteCount();
        return count;
    }

    /**
     * Get the total number of passed tests.
     * If this is a parent then the sum of all the children's passed tests.
     * If this is a child this returns 1 for a passed test otherwise 0.
     * @return  the total number of passed tests
     */
    public int getPassCount()
    {
        if (!this.isParent())
            return this.pass ? 1 : 0;
        int count=0;
        for (TestResult result : this.children)
            count+= result.getPassCount();
        return count;
    }

    /**
     * Get the total number of tests.
     * If this is a parent then the sum of all the children's tests.
     * If this is a child this returns 1.
     * @return  the total number of tests
     */
    public int getTestCount()
    {
        if (!this.isParent())
            return 1;
        int count=0;
        for (TestResult result : this.children)
            count+= result.getTestCount();
        return count;
    }

    /**
     * Get the report for this test.
     * @return  result of {@code this.getReport(true, true)}
     * @see     TestResult#getReport(boolean, boolean)
     */
    public String getReport()
    {
        return this.getReport(true, true);
    }

    /**
     * Get the report for this test
     * The report is in the format:
     * <pre>
     * Tests     [getTestCount()]
     * Completed [getCompleteCount()]
     * Passed    [getPassCount())]
     *
     * Name                                               Complete     Pass
     * ================================================== ======== ========
     * [test name]                                        [YES/NO] [YES/NO]
     * [message]
     * [exception]
     *    [999] [parent test]                                [999]    [999]
     * </pre>
     * As an example:
     * <pre>
     * Tests     4
     * Completed 2
     * Passed    1
     *
     * Name                                               Complete     Pass
     * ================================================== ======== ========
     * passed                                                  YES      YES
     * failed                                                  YES       NO
     * message why the test failed
     * incomplete                                               NO       NO
     * message why the test did not complete
     * exception                                                NO       NO
     *   &gt;&gt; java.lang.IllegalArgumentException: That did not work!
     *   &gt;&gt; Cause:
     *   &gt;&gt; lxTest.BadTests.exception at 66
     *   &gt;&gt; sun.reflect.NativeMethodAccessorImpl.invoke0 at -2
     *   &gt;&gt; sun.reflect.NativeMethodAccessorImpl.invoke at 62
     *   &gt;&gt; sun.reflect.DelegatingMethodAccessorImpl.invoke at 43
     *   &gt;&gt; java.lang.reflect.Method.invoke at 483
     *   &gt;&gt; lexa.test.TestClass.methodCallResult at 291
     *        4 parent test                                      2        1
     * </pre>
     * @param details
     *          Flag if the report should include details of tests that passed
     * @param exceptions
     *          Flag if the report should include details of exceptions' stacks
     * @return  A report of the results for this test.cd ..\
     */
    public String getReport(boolean details, boolean exceptions)
    {
        StringBuilder report = new StringBuilder();
        this.buildReport(report, true, details, exceptions);

        return report.toString();
    }

    private void buildReport(StringBuilder report,boolean topLevel, boolean details, boolean exceptions)
    {
        if (topLevel)
        {
            report.append(this.getName()).append(":-\n")
                    .append("Tests     ").append(this.getTestCount()).append('\n')
                    .append("Completed ").append(this.getCompleteCount()).append('\n')
                    .append("Passed    ").append(this.getPassCount()).append('\n');
            if (details || !this.passed())
            {
                report
                    .append("\nName                                                                 Complete     Pass Time(ms)\n")
                    .append("====================================================================== ======== ======== ========\n");
            }
        }

        if (this.isParent() && (exceptions || details || !this.passed()))
        {
            for (TestResult result : this.children)
                result.buildReport(report, false, details, exceptions);
        }

        if (details || !this.passed())
        {
            if (this.isParent())
            {
                report
                        .append(String.format("%8d", this.getTestCount()))
                        .append(" ")
                        .append(String.format("%1$-61s", this.getName()))
                        .append(String.format("%9d", this.getCompleteCount()))
                        .append(String.format("%9d", this.getPassCount()))
                        .append(this.getElapsedTime() > 1 ?
                                String.format("%9d", this.getElapsedTime()) :
                                "        -")
                        .append('\n');
            }
            else
            {
                report
                        .append(String.format("%1$-70s", this.getName()))
                        .append(this.completed() ?
                                "      YES" :
                                "       NO")
                        .append(this.passed()    ?
                                "      YES" :
                                "       NO")
                        .append(this.getElapsedTime() > 1 ?
                                String.format("%9d", this.getElapsedTime()) :
                                "        -")
                        .append('\n');
                if (this.message != null)
                {
                    report
                            .append("     ")
                            .append(this.message)
                            .append('\n');
                }
            }
        }

        Throwable ex =  this.getException();
        if (ex != null)
        {
            report.append("  >> ")
                    .append(this.getException())
                    .append('\n');
            if (exceptions)
            {
                report.append("  >> Cause:\n");
                for ( StackTraceElement ste : ex.getStackTrace())
                {
                    report.append("  >> ")
                            .append(ste.getClassName())
                            .append('.')
                            .append(ste.getMethodName())
                            .append(" at ")
                            .append(ste.getLineNumber())
                            .append('\n');
                    if (ste.getClassName().startsWith("lexa.test."))
                            break;
                }
            }
        }
    }

    /**
     * Did the test pass
     * If a child node did it pass, otherwise did all of the children pass.
     * @return  {@code true} if the test passed,
     *          otherwise {@code false}
     */
    public boolean passed()
    {
        if (!this.isParent())
            return this.pass;
        for (TestResult result : this.children)
            if (!result.passed())
                return false;
        return true;
    }

    /**
     * Did the test complete
     * If a child node did it complete, otherwise did all of the children complete.
     * @return  {@code true} if the test completed,
     *          otherwise {@code false}
     */
    public boolean completed()
    {
        if (!this.isParent())
            return this.complete;
        for (TestResult result : this.children)
            if (!result.completed())
                return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "TestResult{" + name +
                ", complete=" + complete + ", pass=" + pass + ", exception=" + exception + '}';
    }

    /**
     * check that all the tests passed result will be a pass or the first failed test
     * @param results
     *          a list of results to be checked
     * @return  the first result which did not pass or else a simple pass.
     */
    public static TestResult all(TestResult ... results)
    {
        for (TestResult result : results)
        {
            if (!result.passed())
            {
                return result;
            }
        }
        return TestResult.result(true);
    }

    /**
     * Is the result assignable to a given class.
     *
     * @param   className
     *          Name of the class the result must be assignable to
     * @param   result
     *          the result to compare
     * @return  If the {@code result} is assignable to the named class then
     *          {@code true}, otherwise {@code false}
     */
    public static TestResult assignableTo(String className, Object result)
    {
        TestResult tr = TestResult.isClass(className, result);
        if (tr.pass || result == null)
        {
            return tr;
        }
        // check with assignable
        Class<?> cl;
        try
        {
            cl = ClassLoader.getSystemClassLoader().loadClass(className);
        }
        catch (ClassNotFoundException ex)
        {
            return new TestResult("assignableTo",false, false, ex);
        }
        return TestResult.result(true, cl.isAssignableFrom(result.getClass())
                , "Result can not be assigned to " + className);
    }

    /**
     * Is the result the given class.
     *
     * @param   className
     *          Name of the class the result must be
     * @param   result
     *          the result to compare
     * @return  If the {@code result} is the named class then {@code true},
     *          otherwise {@code false}
     */
    public static TestResult isClass(String className, Object result)
    {
        return TestResult.result(className,
                result == null ? null : result.getClass().getCanonicalName(),
                "Result is not an instance of " + className);
    }

    /**
     * Is the result {@code null}
     *
     * @param   result
     *          the result to compare
     * @return  If the {@code result} is {@code null} then {@code true},
     *          otherwise {@code false}
     */
    public static TestResult isNull(Object result)
    {
        return TestResult.result(null, result, "Result is not null");
    }

    /**
     * Is the result not {@code null}
     *
     * @param   result
     *          the result to compare
     * @return  If the {@code result} is not {@code null} then {@code true},
     *          otherwise {@code false}
     */
    public static TestResult notNull(Object result)
    {
        return TestResult.result(true, result != null, "Result is null");
    }

    /**
     * Is the result {@code true}
     *
     * @param   result
     *          the result to compare
     * @return  If the {@code result} is {@code true} then {@code true},
     *          otherwise {@code false}
     */
    public static TestResult result(boolean result)
    {
        return TestResult.result(true, result, "Test returned false");
    }

    /**
     * Is the result equal to the expected value.
     *
     * @param   expected
     *          the expected value
     * @param   result
     *          the result to compare
     * @return  If the {@code result} equals {@code expected} then {@code true},
     *          otherwise {@code false}
     */
    public static TestResult result(Object expected, Object result)
    {
        return TestResult.result(expected, result,
                "Expected result of [" + expected + "], result was [" + result + "]");
    }

    /**
     * Is the result equal to the expected value.
     *
     * @param   expected
     *          the expected value
     * @param   result
     *          the result to compare
     * @param   message
     *          the message to include if the test failed.
     * @return  If the {@code result} equals {@code expected} then {@code true},
     *          otherwise {@code false}
     */
    public static TestResult result(Object expected, Object result, String message)
    {
        boolean passed = (expected == null && result == null) ||
                (expected != null && expected.equals(result));
        return new TestResult(passed, message);
    }

    /**
     * Is the result in the expected range.
     *
     * @param   min
     *          the minimum acceptable value
     * @param   max
     *          the maximum acceptable value
     * @param   result
     *          the result to compare
     * @return  If the {@code result} is between {@code min} and {@code max}
     *          then {@code true}, otherwise {@code false}
     */
    public static TestResult result(Double min, Double max, Double result)
    {
        return TestResult.result(true, (result>= min && result <= max),
                "Expected result between [" + min +
                        "], and [" + max +
                        "], result was [" + result + "]");
    }
}
