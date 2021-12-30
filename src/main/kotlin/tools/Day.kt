package tools

@Suppress("MemberVisibilityCanBePrivate")
abstract class Day(year: Int, day: Int, input: String, detail: String) {
    constructor(year: Int, day: Int, test: Int? = null) : this(
        year,
        day,
        test?.let { "test$it.txt" } ?: "input.txt",
        test?.let { " test $it" } ?: ""
    )

    val part1: Any by lazy {
        log("$info part 1:")
        solvePart1().log()
    }

    val part2: Any by lazy {
        log("$info part 2:")
        solvePart2().log()
    }

    fun check(expectedPart1: Any, expectedPart2: Any) {
        checkPart1(expectedPart1)
        checkPart2(expectedPart2)
    }

    fun checkPart1(expectedPart1: Any) {
        if (part1.toString() != expectedPart1.toString()) error("$info invalid part1")
    }

    fun checkPart2(expectedPart2: Any) {
        if (part2.toString() != expectedPart2.toString()) error("$info invalid part2")
    }

    protected val lines = javaClass.classLoader!!.getResource("aoc$year/day$day/$input")!!.readText().lines()

    protected fun log(message: Any?) = System.err.println(message)
    protected fun <T> T.log() = apply { log(this) }

    protected abstract fun solvePart1(): Any
    protected abstract fun solvePart2(): Any

    private val info = "year $year day $day$detail"
}
