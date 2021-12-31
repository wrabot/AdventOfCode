package aoc2020

import tools.Day

class Day6 : Day(2020, 6) {
    override fun solvePart1() = groups.sumOf { it.joinToString("").toSet().count() }
    override fun solvePart2() = groups.sumOf { g -> g.map { it.toSet() }.reduce { acc, s -> acc intersect s }.count() }

    private val groups = input.split("\n\n").map { it.lines() }
}
