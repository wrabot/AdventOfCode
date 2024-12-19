package aoc2024

import Day

class Day19(test: Int? = null) : Day(test) {
    private val patterns = lines[0].split(", ")
    private val designs = lines.drop(2)

    override fun solvePart1() = designs.count { it.isPossibleWith() }

    private val isPossibleWith = mutableMapOf<String, Boolean>()
    private fun String.isPossibleWith(): Boolean = isEmpty() || isPossibleWith.getOrPut(this) {
        patterns.any { startsWith(it) && removePrefix(it).isPossibleWith() }
    }

    override fun solvePart2() = designs.sumOf { it.ways() }

    private val ways = mutableMapOf<String, Long>()
    private fun String.ways(): Long = if (isEmpty()) 1 else ways.getOrPut(this) {
        patterns.sumOf { if (startsWith(it)) removePrefix(it).ways() else 0 }
    }
}
