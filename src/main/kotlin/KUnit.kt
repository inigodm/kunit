import java.lang.reflect.Method
import java.util.function.Predicate

open class TestSuite(){
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

open class TestCase(var methodFilter: Predicate<Method> = Predicate{it.name.startsWith("test")}){
    fun run(): TestResult{
        var result = TestResult()
        setup()
        try {
            findMethods().forEach{
                result.testStarted();
                it.invoke(this);
            }
        } catch (e: Exception) {
            result.testFailed()
        }
        tearDown()
        return result
    }

    private fun findMethods(): List<Method>{
        return this.javaClass.methods
            .filter(methodFilter::test)
            .toList()
    }

    fun setup(){
    }

    fun tearDown(){
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