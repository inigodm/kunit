import org.junit.Assert

fun main(args : Array<String>) {
    var test = TestTestCase("testMethod")
    test.setUp()
    test.test()
    test.testFailedResult()
    test.testTestResult()
    test.testSuite()
}

class TestTestCase(var methodName: String){
    lateinit var test: TestCaseRun;

    fun setUp(){
        test = TestCaseRun(methodName)
    }

    fun testSuite(){
        var suite = TestSuite()
        suite.add(TestCaseRun("testMethod"))
        suite.add(TestCaseRun("testBroken"))
        var result = suite.run()
        Assert.assertTrue("2 test run, 1 failed" == result.summary())
    }

    fun test(){
        val result = test.run()
        println(test.log)
        Assert.assertTrue(test.log == "setup method executed $methodName teardown")
        Assert.assertTrue(result.summary() == "1 test run, 0 failed")
    }

    fun testTestResult(){
        var result = TestResult()
        result.testStarted()
        result.testFailed()
        Assert.assertTrue(result.summary() == "1 test run, 1 failed")
    }

    fun testFailedResult(){
        test = TestCaseRun("fakemethod")
        val result = test.run()
        Assert.assertTrue(result.summary() == "1 test run, 1 failed")
    }
}

class TestCaseRun(methodName: String): TestCase(methodName) {
    fun testMethod(){
        log += "method executed "
    }
}

