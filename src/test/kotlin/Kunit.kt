import org.junit.Assert

fun main(args : Array<String>) {
    var test = TestTestCase("testMethod")
    test.setUp()
    test.testSetUp()
    test.test()
}

class TestTestCase(var methodName: String){
    lateinit var test: TestCaseRun;

    fun setUp(){
        test = TestCaseRun(methodName)
    }

    fun testSetUp(){
        test.run()
        Assert.assertTrue(test.log == "setup ")
    }

    fun test(){
        test.run()
        Assert.assertTrue(test.wasRun)
    }
}

open class TestCase(var methodName: String){
    var wasRun: Boolean = false;
    var log: String = ""

    fun run(){
        setup()
        val cls = this.javaClass
        cls.getMethod(methodName).invoke(this)
    }

    fun setup(){
        wasRun = true;
        log += "setup "
    }
}

class TestCaseRun(methodName: String): TestCase(methodName) {
    fun testMethod(){
        log += "testMethod "
    }
}

