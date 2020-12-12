import org.junit.Rule
import org.junit.rules.TestName

open class BaseTest(private val root: String) {
    @Rule
    @JvmField
    val testName = TestName()

    fun test(inputCount: Int, block: (List<String>) -> Unit) {
        val name = "$root/${testName.methodName}"
        log("start day $name")
        for (index in 1..inputCount) {
            log("start day input $index")
            block(resource("/$name/input$index.txt").lines())
        }
    }

    fun log(message: Any?) = System.err.println("!!!$message")

    fun List<Char>.log(width: Int) = apply {
        log(chunked(width) { it.joinToString("") }.joinToString("\n"))
    }

    fun <T> T.log() = apply { log(this) }

    private fun resource(name: String) = name.javaClass.getResource(name)!!.readText()
}
