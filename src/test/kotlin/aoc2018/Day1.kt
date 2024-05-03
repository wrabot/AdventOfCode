package aoc2018

import Day

class Day1(test: Int? = null) : Day(test) {
    override fun solvePart1() = values.sum()

    val cache = mutableSetOf(0)
    override fun solvePart2(): Int {
        var current = 0
        while (true) {
            values.forEach {
                current += it
                if (!cache.add(current)) return current
            }
        }
    }

    val values = lines.map { it.toInt() }
}
