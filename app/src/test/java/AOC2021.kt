import org.junit.Test

class AOC2021 : BaseTest("AOC2021") {
    @Test
    fun day1() = test(1, 2) { lines ->
        val depths = lines.map { it.toInt() }
        depths.zipWithNext().count { it.first < it.second }.log()
        depths.windowed(3).map { it.sum() }.zipWithNext().count { it.first < it.second }.log()
    }
}
