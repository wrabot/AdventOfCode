import org.junit.Rule
import org.junit.rules.TestName
import tools.log

open class BaseTest(private val root: String) {
    @Rule
    @JvmField
    val testName = TestName()

    fun test(vararg inputs: Int, block: (List<String>) -> Unit) {
        val name = "$root/${testName.methodName}"
        inputs.forEach {
            log("start day $name input $it")
            block(resource("/$name/input$it.txt").lines())
        }
    }

    private fun resource(name: String) = javaClass.getResource(name)!!.readText()
}
