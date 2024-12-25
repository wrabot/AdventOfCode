package aoc2024

import Day

class Day25(test: Int? = null) : Day(test) {
    private val schematics = input.split("\n\n").map {
        val lines = it.lines()
        (it[0] == '#') to (0..4).map { c -> lines.count { it[c] == '#' } - 1 }
    }

    override fun solvePart1(): Int {
        val (locks, keys) = schematics.partition { it.first }
        return locks.sumOf { lock -> keys.count { key -> (0..4).all { lock.second[it] + key.second[it] <= 5 } } }
    }

    override fun solvePart2() = Unit
}
