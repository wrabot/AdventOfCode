import org.junit.Test

class AOC2021 : BaseTest("AOC2021") {
    @Test
    fun day1() = test(1, 2) { lines ->
        val depths = lines.map { it.toInt() }
        //part1
        depths.zipWithNext().count { it.first < it.second }.log()
        //part2
        depths.windowed(3).map { it.sum() }.zipWithNext().count { it.first < it.second }.log()
    }

    @Test
    fun day2() = test(1, 2) { lines ->
        val orders = lines.map {
            val (action, value) = it.split(" ")
            action to value.toInt()
        }

        var position = 0
        var depth = 0

        //part1
        orders.forEach {
            when (it.first) {
                "forward" -> position += it.second
                "up" -> depth -= it.second
                "down" -> depth += it.second
            }
        }
        (position * depth).log()

        //part2
        position = 0
        depth = 0
        var aim = 0
        orders.forEach {
            when (it.first) {
                "forward" -> {
                    position += it.second
                    depth += it.second * aim
                }
                "up" -> aim -= it.second
                "down" -> aim += it.second
            }
        }
        (position * depth).log()
    }
}
