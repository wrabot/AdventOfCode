package tools

import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@Suppress("MemberVisibilityCanBePrivate")
abstract class Day(year: Int, day: Int, inputFileName: String, detail: String, private val measure: Boolean) {
    constructor(year: Int, day: Int, test: Int? = null, measure: Boolean = false) : this(
        year,
        day,
        test?.let { "test$it.txt" } ?: "input.txt",
        test?.let { " test $it" } ?: "",
        measure
    )

    val part1: Any by lazy {
        solvePart1().apply { println("$info part 1: $this") }
    }

    val part2: Any by lazy {
        solvePart2().apply { println("$info part 2: $this") }
    }

    fun check(expectedPart1: Any, expectedPart2: Any) {
        checkPart1(expectedPart1)
        checkPart2(expectedPart2)
    }

    fun checkPart1(expectedPart1: Any) {
        measure {
            if (part1.toString() != expectedPart1.toString()) log("$info invalid part1: $part1")
        }
    }

    fun checkPart2(expectedPart2: Any) {
        measure {
            if (part2.toString() != expectedPart2.toString()) log("$info invalid part2: $part2")
        }
    }

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(ExperimentalTime::class)
    private fun measure(block: () -> Unit) {
        if (measure) measureTime(block).log() else block()
    }

    protected val input = javaClass.classLoader!!.getResource("aoc$year/day$day/$inputFileName")!!.readText()
    protected val lines = input.lines()

    protected fun log(message: Any?) = System.err.println(message)
    protected fun <T> T.log() = apply { log(this) }

    protected abstract fun solvePart1(): Any
    protected abstract fun solvePart2(): Any

    private val info = "year $year day $day$detail"
}
