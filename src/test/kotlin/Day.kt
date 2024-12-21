import org.junit.jupiter.api.Assertions
import tools.debug
import kotlin.time.measureTime

abstract class Day(resourceName: String) {
    constructor(test: Int? = null) : this(test?.let { "test$it" } ?: "input")

    val part1: Any by lazy {
        solvePart1().apply { println("$info part 1: ${display()}") }
    }

    private val part2: Any by lazy {
        solvePart2().apply { println("$info part 2: ${display()}") }
    }

    private fun Any.display() = toString().let { if (it.lines().size == 1) it else "\n$it" }

    fun check(expectedPart1: Any, expectedPart2: Any) {
        checkPart1(expectedPart1)
        checkPart2(expectedPart2)
    }

    fun checkPart1(expectedPart1: Any) {
        measureTime {
            Assertions.assertEquals(expectedPart1.toString(), part1.toString())
        }.debug()
    }

    fun checkPart2(expectedPart2: Any) {
        measureTime {
            Assertions.assertEquals(expectedPart2.toString(), part2.toString())
        }.debug()
    }

    private val path = javaClass.name.replace('.', '/').lowercase()
    protected val input = javaClass.classLoader!!.getResource("$path/$resourceName.txt")!!.readText()
    protected val lines = input.lines()

    protected abstract fun solvePart1(): Any
    protected abstract fun solvePart2(): Any

    private val info = "$path $resourceName"
}
