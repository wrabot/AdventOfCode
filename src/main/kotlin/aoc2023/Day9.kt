package aoc2023

import Day

class Day9(test: Int? = null) : Day(2023, 9, test) {
    override fun solvePart1() = report.sumOf { it.findNext() }
    private fun List<Int>.findNext(): Int =
        if (all { it == 0 }) 0 else last() + zipWithNext().map { it.second - it.first }.findNext()

    override fun solvePart2() = report.sumOf { it.findPrevious() }

    private fun List<Int>.findPrevious(): Int =
        if (all { it == 0 }) 0 else first() - zipWithNext().map { it.second - it.first }.findPrevious()

    private val report = lines.map { line -> line.split(" ").map { it.toInt() } }
}
