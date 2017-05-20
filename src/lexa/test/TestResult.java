/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexa.test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author william
 */
public class TestResult
{

    private final String message;
    private final String name;
    private final Boolean complete;
    private final Boolean pass;
    private final Throwable exception;
    private final List<TestResult> children;

    public TestResult(String name, TestResult rename)
    {
        this.name = name;
        this.complete = rename.complete;
        this.pass = rename.pass;
        this.exception = rename.exception;
        this.children = rename.children;
        this.message = rename.message;
    }
    /**
     *
     * @param name
     */
    public TestResult(String name)
    {
        this.name = name;
        this.complete = null;
        this.pass = null;
        this.exception = null;
        this.message = null;
        this.children = new ArrayList();
    }
    /**
     *
     * @param pass
     */
    public TestResult(boolean pass)
    {
        this(pass, null);
    }
    public TestResult(boolean pass, String message)
    {
        this(null, true, pass, null, message);
    }    /**
     *
     * @param name
     * @param pass
     */
    public TestResult(String name, boolean pass)
    {
        this(name, pass, null);
    }
    public TestResult(String name, boolean pass, String message)
    {
        this(name, true, pass, null, message);
    }
    /**
     *
     * @param complete
     * @param pass
     * @param exception
     */
    public TestResult(boolean complete, boolean pass, Throwable exception)
    {
        this(null,complete, pass, exception);
    }

    /**
     *
     * @param name
     * @param complete
     * @param pass
     * @param exception
     */
    public TestResult(String name, boolean complete, boolean pass, Throwable exception)
    {
        this(name, true, true, exception, null);
    }
    public TestResult(String name, boolean complete, boolean pass, Throwable exception, String message)
    {

        this.name = name;
        // The three items MUST be consistant:
        this.pass = pass && complete && (exception == null);
        this.complete = complete && (exception == null);
        this.exception = exception;
        this.children = null;
        this.message = this.pass ? null : message;
    }

    /**
     *
     * @return
     */
    public boolean isParent()
    {
        return (this.children != null);
    }

    /**
     *
     * @param result
     * @return
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
     *
     * @param result
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
     *
     * @return
     */
    public String getName()
    {
        return this.name;
    }

    /**
     *
     * @return
     */
    public Throwable getException()
    {
        return this.exception;
    }

    /**
     *
     * @return
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
     *
     * @return
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
     *
     * @return
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
     *
     * @return
     */
    public String getReport()
    {
        return this.getReport(true, true);
    }

    /**
     *
     * @param details
     * @param exceptions
     * @return
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
                    .append("\nName                                             Complete     Pass\n")
                    .append("================================================== ======== ========\n");
            }
        }

        if (this.isParent() && (exceptions || details))
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
                        .append(String.format("%1$-41s", this.getName()))
                        .append(String.format("%9d", this.getCompleteCount()))
                        .append(String.format("%9d", this.getPassCount()))
                        .append('\n');
            }
            else
            {
                report
                        .append(String.format("%1$-50s", this.getName()))
                        .append(this.completed() ? "      YES" : "       NO")
                        .append(this.passed() ? "      YES" : "       NO")
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
        if (exceptions)
        {
            Throwable ex =  this.getException();
            if (ex != null)
            {
                report.append("  >> ")
                        .append(this.getException())
                        .append('\n')
                        .append("  >> Cause:\n");
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
     *
     * @return
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
     *
     * @return
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

    public static TestResult isNull(Object result)
    {
        return TestResult.result(null, result, "Result is not null");
    }

    public static TestResult notNull(Object result)
    {
        return TestResult.result(true, result != null, "Result is null");
    }

    public static TestResult result(boolean result)
    {
        return TestResult.result(true, result, "Test returned false");
    }

    public static TestResult result(Object expected, Object result)
    {
        return TestResult.result(expected, result,
                "Expected result of [" + expected + "], result was [" + result + "]");
    }
    public static TestResult result(Object expected, Object result, String message)
    {
        boolean passed = (expected == null && result == null) ||
                (expected != null && expected.equals(result));
        return new TestResult(passed, message);
    }
}
