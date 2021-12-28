package tools

abstract class Day(year: Int, day: Int, input: String, detail: String) {
    constructor(year: Int, day: Int, test: Int? = null) : this(
        year,
        day,
        test?.let { "test$it.txt" } ?: "input.txt",
        test?.let { " test $it" } ?: ""
    )

    private val info = "year $year day $day$detail"
    protected val lines = javaClass.classLoader!!.getResource("aoc$year/day$day/$input")!!.readText().lines()

    protected fun log(message: Any?) = System.err.println(message)
    protected fun <T> T.log() = apply { log(this) }

    fun solve() {
        solvePart1()
        solvePart2()
    }

    fun solvePart1() = getPart1().apply { log("$info part 1:\n$this") }
    fun solvePart2() = getPart2().apply { log("$info part 2:\n$this") }

    fun check(part1: Any, part2: Any) {
        if (solvePart1().toString() != part1.toString()) error("$info invalid part1")
        if (solvePart2().toString() != part2.toString()) error("$info invalid part2")
    }

    protected abstract fun getPart1(): Any
    protected abstract fun getPart2(): Any
}
