import org.junit.Assert

fun main(args : Array<String>) {
    var test = TestTestCase("testMethod")
    test.setUp()
    test.test()
    test.testFailedResult()
    test.testTestResult()
}

class TestTestCase(var methodName: String){
    lateinit var test: TestCaseRun;

    fun setUp(){
        test = TestCaseRun(methodName)
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

open class TestCase(var methodName: String){
    var log: String = ""
    var result = TestResult()

    fun run(): TestResult{
        result.testStarted()
        setup()
        try {
            val cls = this.javaClass
            cls.getMethod(methodName).invoke(this)
            log += "$methodName "
        } catch (e: Exception) {
            result.testFailed()
        }
        tearDown()
        return result
    }

    fun setup(){
        log = "setup "
    }

    fun tearDown(){
        log += "teardown"
    }

}

class TestResult(var runCount: Int = 0, var failCount: Int = 0){
    fun testStarted(){
        runCount++;
    }

    fun testFailed(){
        failCount++;
    }

    fun summary(): String{
        return "$runCount test run, $failCount failed"
    }
}

class TestCaseRun(methodName: String): TestCase(methodName) {
    fun testMethod(){
        log += "method executed "
    }
}

