package aoc2021

import Day

class Day1(test: Int? = null) : Day(test) {
    override fun solvePart1() = depths.zipWithNext().count { it.first < it.second }
    override fun solvePart2() = depths.windowed(3).map { it.sum() }.zipWithNext().count { it.first < it.second }

    private val depths = lines.map { it.toInt() }
}
