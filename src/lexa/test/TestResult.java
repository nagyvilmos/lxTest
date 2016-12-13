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
    private final String name;
    private final Boolean complete;
    private final Boolean pass;
    private final Throwable exception;
    private final List<TestResult> children;
    
    public TestResult(String name)
    {
        this.name = name;
        this.complete = null;
        this.pass = null;
        this.exception = null;
        this.children = new ArrayList();
    }

    public TestResult(String name, boolean pass)
    {
        this(name, true, pass, null);
    }
    
    public TestResult(String name, boolean complete, boolean pass, Throwable exception)
    {
        this.name = name;
        // The three items MUST be consistant:
        this.pass = pass && complete && (exception == null);
        this.complete = complete && (exception == null);;
        this.exception = exception;
        this.children = null;
    }

    public boolean isParent()
    {
        return (this.children != null);
    }
    public boolean addResultIfFailed(TestResult result)
    {
        if (!result.passed())
        {
            this.addResult(result);
            return true;
        }
        return false;
    }
    public void addResult(TestResult result)
    {
        if (!this.isParent())
            throw new IllegalArgumentException("Can only add children to a parent");
        this.children.add(result);
    }
    public String getName()
    {
        return this.name;
    }

    public Throwable getException()
    {
        return this.exception;
    }

    public int getCompleteCount()
    {
        if (!this.isParent())
            return this.complete ? 1 : 0;
        int count=0;
        for (TestResult result : this.children)
            count+= result.getCompleteCount();
        return count;
    }

    public int getPassCount()
    {
        if (!this.isParent())
            return this.pass ? 1 : 0;
        int count=0;
        for (TestResult result : this.children)
            count+= result.getPassCount();
        return count;
    }
    public int getTestCount()
    {
        if (!this.isParent())
            return 1;
        int count=0;
        for (TestResult result : this.children)
            count+= result.getTestCount();
        return count;
    }

    public String getReport()
    {
        return this.getReport(true, true);
    }
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
            }
        }
        if (exceptions && this.getException() != null)
        {
            report.append(this.getException())
                    .append('\n');
        }
    }

    public boolean passed()
    {
        if (!this.isParent())
            return this.pass;
        for (TestResult result : this.children)
            if (!result.passed())
                return false;
        return true;
    }

    public boolean completed()
    {
        if (!this.isParent())
            return this.complete;
        for (TestResult result : this.children)
            if (!result.completed())
                return false;
        return true;
    }
}
