package aoc2015

import Day
import tools.sequence.select

class Day24(test: Int? = null) : Day(test) {
    override fun solvePart1() = split(3)

    override fun solvePart2() = split(4)

    private fun split(part: Int): Long {
        val groupWeight = totalWeight / part
        return (1..weights.size).firstNotNullOf { length ->
            weights.select(length).filter { it.sum() == groupWeight }.minOfOrNull { it.reduce { acc, i -> acc * i } }
        }
    }

    private val weights = lines.map { it.toLong() }
    private val totalWeight = weights.sum()
}


