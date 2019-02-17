import org.junit.Assert
import java.lang.Exception
import java.util.function.Predicate

fun main(args : Array<String>) {
    var test = TestTestCase("testMethod")
    test.setUp()
    test.test()
    test.testFailedResult()
    test.testTestResult()
    test.testSuite()
    test = TestTestCase("testMethod")
    test.testRunAllMethodsInTestCase()
}

class TestTestCase(var methodName: String){
    lateinit var test: TestCaseRun;

    fun setUp(){
        test = TestCaseRun(methodName)
    }

    fun testRunAllMethodsInTestCase(){
        var testtestsuite = TestTestCase2()
        var result = testtestsuite.run()
        Assert.assertTrue(result.summary() == "1 test run, 0 failed")
        // Three methods, two test
        var t = TestTestCase3()
        result = t.run()
        Assert.assertTrue(result.summary() == "2 test run, 0 failed")

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

class TestCaseRun(methodName: String): TestCase(Predicate{it.name.equals(methodName)}) {
    fun testMethod(){
        log += "method executed "
    }

    fun testBroken(){
        throw Exception()
    }

    fun fakemethod(){
        throw Exception()
    }
}

class TestTestCase2(): TestCase(){
    fun testInnerMethod(){
        println("innermethod")
    }
}

class TestTestCase3(): TestCase(){
    fun testInnerMethod(){
        println("innermethod")
    }

    fun testInnerMethod2(){
        println("innermethod2")
    }
}
