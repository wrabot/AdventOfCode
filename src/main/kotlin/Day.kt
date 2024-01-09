import tools.log
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTime

abstract class Day(inputFileName: String, detail: String, private val measure: Boolean) {
    constructor(test: Int? = null, measure: Boolean = false) : this(
        test?.let { "test$it.txt" } ?: "input.txt",
        test?.let { " test $it" } ?: "",
        measure
    )

    val part1: Any by lazy {
        solvePart1().apply { println("$info part 1: $this") }
    }

    private val part2: Any by lazy {
        solvePart2().apply { println("$info part 2: $this") }
    }

    fun check(expectedPart1: Any, expectedPart2: Any) {
        val duration = measureTime {
            checkPart1(expectedPart1)
            checkPart2(expectedPart2)
        }
        if (duration > 1.seconds) log("$info is slow: $duration")
    }

    fun checkPart1(expectedPart1: Any) {
        measure("$info part1") {
            if (part1.toString() != expectedPart1.toString()) log("$info invalid part1: $expectedPart1")
        }
    }

    fun checkPart2(expectedPart2: Any) {
        measure("$info part2") {
            if (part2.toString() != expectedPart2.toString()) log("$info invalid part2: $expectedPart2")
        }
    }

    private fun measure(message: String, block: () -> Unit) {
        if (measure) measureTime(block).apply { log("$message: $this") } else block()
    }

    private val path = javaClass.name.replace('.', '/').lowercase()
    protected val input = javaClass.classLoader!!.getResource("$path/$inputFileName")!!.readText()
    protected val lines = input.lines()

    protected abstract fun solvePart1(): Any
    protected abstract fun solvePart2(): Any

    private val info = "$path $detail"
}
