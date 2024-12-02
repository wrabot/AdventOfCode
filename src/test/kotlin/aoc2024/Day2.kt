package aoc2024

import Day
import tools.text.toInts

class Day2(test: Int? = null) : Day(test) {
    private val reports = lines.map { it.toInts() }

    override fun solvePart1() = reports.count { it.isSafe() }

    override fun solvePart2() = reports.count { report ->
        report.isSafe() || report.indices.any { report.filterIndexed { index, _ -> index != it }.isSafe() }
    }

    private fun List<Int>.isSafe(): Boolean {
        val differences = zipWithNext { a, b -> a - b }
        val range = if (differences.first() > 0) 1..3 else -3..-1
        return differences.all { it in range }
    }
}
