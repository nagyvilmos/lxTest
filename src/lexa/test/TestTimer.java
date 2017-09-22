/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * TestTimer.java (lxTest)
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2017
 *==============================================================================
 */

package lexa.test;

import java.util.*;

/**
 * Time how long a test takes.
 * @author  willaimnw
 * @since   2017-09
 */
public class TestTimer
{
    /** start time for the test */
    private final long startTime;

    /**
     * Create a new timer
     * <p>The timer is marked with a start time and calling
     * {@link TestTimer#done(lexa.test.TestResult) done} will return the results
     * with the added elapsed time in milliseconds.
     */
    public TestTimer()
    {
        java.lang.Runtime.getRuntime().maxMemory();
        this.startTime = new Date().getTime();
    }

    /**
     * Add the elapsed time when a test is done
     * @param   result
     *          Result to add time to
     * @return
     */
    TestResult done(TestResult result)
    {
        long elapsed = new Date().getTime() - this.startTime;
        return new TestResult(result, elapsed);
    }
}
