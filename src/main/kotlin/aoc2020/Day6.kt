package aoc2020

import tools.Day

class Day6 : Day(2020, 6) {
    override fun solvePart1() = groups.sumOf { it.reduce { acc, s -> acc + s }.toSet().count() }
    override fun solvePart2() = groups.sumOf { g -> g.map { it.toSet() }.reduce { acc, s -> acc intersect s }.count() }

    //TODO remove mutable ?
    private val groups = mutableListOf(mutableListOf<String>()).apply {
        lines.forEach { if (it.isBlank()) add(mutableListOf()) else last().add(it) }
    }
}
