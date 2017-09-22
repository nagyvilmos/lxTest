/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * TesRun.java (lxTest)
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: December 2016
 *==============================================================================
 */
package lexa.test;

/**
 * Container for a list of tests to be run.
 * This provides a convenient container to run a set of tests.  The class is
 * instantiated with one or more {@link TestClassInterface test} to be performed.
 * Optionally a list of tests can be added to be performed.
 *
 * @author william
 * @since  2016-12
 */
public class TestRun
{
    /** the list of tests to be performed */
    private final TestClassInterface[] classList;

    /**
     * Create a test run for the supplied tests.
     *
     * @param classList the tests to perform.
     */
    public TestRun(TestClassInterface ... classList)
    {
        this.classList = classList;
    }

    /**
     * Run the tests
     * @return  the result of the tests
     */
    public TestResult execute()
    {
        return this.execute(false);
    }

    /**
     * run the tests
     *
     * @param   stopOnError
     *          indicate if the run should stop when a failure is encountered.
     * @return  the result of the tests
     */
    public TestResult execute(boolean stopOnError)
    {
        TestResult result = new TestResult("lexa.test.TestRun");

        // loop through the test classes
        for (TestClassInterface test : this.classList)
        {
            TestResult tr = test.execute(stopOnError);
            result.addResult(tr);
            if (stopOnError && !tr.passed())
            {
                break;
            }
        }
        return result;
    }
}
