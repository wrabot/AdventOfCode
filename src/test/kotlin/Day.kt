import org.junit.Assert

abstract class Day(inputFileName: String, detail: String) {
    constructor(test: Int? = null) : this(
        test?.let { "test$it.txt" } ?: "input.txt",
        test?.let { " test $it" } ?: "",
    )

    val part1: Any by lazy {
        solvePart1().apply { println("$info part 1: $this") }
    }

    private val part2: Any by lazy {
        solvePart2().apply { println("$info part 2: $this") }
    }

    fun check(expectedPart1: Any, expectedPart2: Any) {
        checkPart1(expectedPart1)
        checkPart2(expectedPart2)
    }

    fun checkPart1(expectedPart1: Any) {
        Assert.assertEquals(expectedPart1.toString(), part1.toString())
    }

    fun checkPart2(expectedPart2: Any) {
        Assert.assertEquals(expectedPart2.toString(), part2.toString())
    }

    private val path = javaClass.name.replace('.', '/').lowercase()
    protected val input = javaClass.classLoader!!.getResource("$path/$inputFileName")!!.readText()
    protected val lines = input.lines()

    protected abstract fun solvePart1(): Any
    protected abstract fun solvePart2(): Any

    private val info = "$path $detail"
}
