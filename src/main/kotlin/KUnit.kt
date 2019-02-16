class TestSuite(){
    var testCases: MutableList<TestCase> = mutableListOf()
    var result = TestResult(0,0)
    fun add(testCase: TestCase){
        testCases.add(testCase)
    }

    fun run(): TestResult{
        testCases.stream().forEach{it -> result += it.run()}
        return result;
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

    operator fun plusAssign(tr: TestResult){
        runCount += tr.runCount
        failCount += tr.failCount
    }
}