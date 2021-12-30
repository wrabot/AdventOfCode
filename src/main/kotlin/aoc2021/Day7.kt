package aoc2021

import tools.Day
import kotlin.math.absoluteValue

class Day7(test: Int? = null) : Day(2021, 7, test) {
    override fun solvePart1() = (min..max).minOf { target ->
        positions.entries.sumOf { (it.key - target).absoluteValue * it.value }
    }

    override fun solvePart2() = (min..max).minOf { target ->
        positions.entries.sumOf {
            val n = (it.key - target).absoluteValue
            n * (n + 1) / 2 * it.value
        }
    }

    private val positions = lines[0].split(",").map { it.toInt() }.groupingBy { it }.eachCount()
    private val min = positions.minOf { it.key }
    private val max = positions.maxOf { it.key }
}
