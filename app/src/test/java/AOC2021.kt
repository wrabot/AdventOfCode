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

    @Test
    fun day3() = test(1, 2) { lines ->
        //part1
        val length = lines[0].length
        val threshold = lines.size / 2
        val gammaBits = (0 until length).map { position ->
            if (lines.count { it[position] == '1' } > threshold) '1' else '0'
        }.joinToString("")
        val epsilonBits = gammaBits.map { if (it == '1') '0' else '1' }.joinToString("")
        (gammaBits.toInt(2) * epsilonBits.toInt(2)).log()

        //part2
        val oxygen = lines.day3Part2('0', '1')
        val co2 = lines.day3Part2('1', '0')
        (oxygen * co2).log()
    }

    private fun List<String>.day3Part2(mostZeroBit: Char, leastOrEqualZeroBit: Char) : Int {
        val length = this[0].length
        var current = this
        for (position in 0 until length) {
            if (current.size == 1) break
            val threshold = current.size / 2
            val bit = if (current.count { it[position] == '0' } > threshold) mostZeroBit else leastOrEqualZeroBit
            current = current.filter { it[position] == bit }
        }
        return current.first().toInt(2)
    }
}
