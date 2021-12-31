package aoc2020

import tools.Day

class Day10(test: Int? = null) : Day(2020, 10, test) {
    override fun solvePart1() = voltages.zipWithNext { a, b -> a - b }.run {
        count { it == 1 } * (count { it == 3 } + 1)
    }

    override fun solvePart2(): Any {
        val counts = mutableMapOf(0 to 1L)
        val links = adapters.groupBy({ it }) { v -> voltages.filter { it in v - 3 until v } }
            .mapValues { it.value.flatten() }.log()
        adapters.reversed().forEach { adapter ->
            counts[adapter] = links[adapter]!!.sumOf { counts[it]!! }
        }
        return counts[adapters.first()]!!
    }

    private val adapters = lines.map { it.toInt() }.sortedDescending()
    private val voltages = adapters + 0
}
