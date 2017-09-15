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

    private final long startTime;

    public TestTimer()
    {
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
        return new TestResult(result,
                new Date().getTime() - this.startTime
        );
    }
}
