/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexa.test;

/**
 *
 * @author william
 */
public class TestRun
{

    private final TestClass[] classList;

    public TestRun(TestClass testClass)
    {
        this(new TestClass[]{testClass}, null);
    }
    public TestRun(TestClass testClass, String[] testList)
    {
        this(new TestClass[]{testClass}, testList);
    }
    public TestRun(TestClass[] classList)
    {
        this(classList, null);
    }
    public TestRun(TestClass[] classList, String[] testList)
    {
        this.classList = classList;
    }
    
    public TestResult execute()
    {
        TestResult result = new TestResult("lexa.test.TestRun");

        // loop through the test classes
        for (TestClass test : this.classList)
        {
            result.addResult(test.execute());
        }
        return result;
    }

//    private TestResult executeTestClass(int index)
//    {
//        boolean pass = true;
//        boolean complete = false;
//        TestClass current = null;
//            current = this.classList[index];
//            Class curClass = current.getClass();
//            System.out.println(curClass.getCanonicalName());
//            // set up the class
//            boolean classSetUp = false;
//            try
//            {
//                classSetUp = current.setUpClass();
//            }
//            catch (Exception ex)
//            {
//                ex.printStackTrace(System.out);
//            }
//            if (classSetUp)
//            {
//                boolean classPass = true;
//                // loop through the tests
//                for (Method method : curClass.getMethods())
//                {
//                    if (method.isAnnotationPresent(TestMethod.class))
//                    {
//                        TestMethod tm = method.getAnnotation(TestMethod.class);
//                        System.out.println(method.getName());
//                        methodCount++;
//                        try {
//                            // set up the test
//                            Method methodSetUp =
//                                    curClass.getMethod(tm.setUp());
//                            System.out.println("> " + methodSetUp.toString());
//                            if ((Boolean)methodSetUp.invoke(current))
//                            {
//                                // run the test
//                                System.out.println("> " + method.toString());
//                                if ((Boolean)method.invoke(current))
//                                {
//                                    // record the result
//                                    methodPassCount++;
//                                }
//                                else
//                                {
//                                    classPass=false;
//                                }
//                                // tear down the test
//                                Method methodTearDown =
//                                        curClass.getMethod(tm.tearDown());
//                                System.out.println("> " + methodTearDown.toString());
//                                if ((Boolean)methodTearDown.invoke(current))
//                                {
//                                    methodCompleteCount++;
//                                }
//                            }
//                        } 
//                        catch (Exception ex)
//                        {
//                            ex.printStackTrace(System.out);
//                            classPass=false;
//                        }
//                    }
//                }
//                // tear down the class
//                if (classPass)
//                {
//                    classPassCount++;
//                }
//                try
//                {
//                    if (current.tearDownClass())
//                    {
//                        classCompleteCount++;
//                    }
//                }
//                catch (Exception ex)
//                {
//                    ex.printStackTrace(System.out);
//                }
//            }
//        }
//    }
}
