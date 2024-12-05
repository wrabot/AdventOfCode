package aoc2024

import Day
import tools.text.toInts

class Day5(test: Int? = null) : Day(test) {
    private val sections = input.split("\n\n")
    private val rules = sections[0].lines().map { it.toInts("|") }.groupBy({ it[0] }, { it[1] })
    private val updates = sections[1].lines().map { it.toInts(",") }

    private val partition = updates.partition { it.zipWithNext().all { (a, b) -> b in rules[a].orEmpty() } }

    override fun solvePart1() = partition.first.sumOf { it[it.size / 2] }

    override fun solvePart2() = partition.second.sumOf {
        it.sortedWith { a, b -> if (b in rules[a].orEmpty()) 1 else -1 }[it.size / 2]
    }
}
