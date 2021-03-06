/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * TestClassInterface.java (lxTest)
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: December 2016
 *==============================================================================
 */
package lexa.test;

/**
 * Interface for a test case
 * The interface is used where the framework
 * will not support the test case or reflection
 * is not required.
 * @author william
 * @since 2016-12
 */
public interface TestClassInterface
{

    /**
     * Execute the test case
     * @param stopOnError stop if an error is encountered.
     * @return The {@link TestResult result} of the tests.
     */
    TestResult execute(boolean stopOnError);

}
