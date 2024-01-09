package aoc2020

import Day

class Day6 : Day() {
    override fun solvePart1() = groups.sumOf { it.joinToString("").toSet().count() }
    override fun solvePart2() = groups.sumOf { g -> g.map { it.toSet() }.reduce { acc, s -> acc intersect s }.count() }

    private val groups = input.split("\n\n").map { it.lines() }
}
