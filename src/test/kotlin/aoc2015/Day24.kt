package aoc2015

import Day
import tools.subLists

class Day24(test: Int? = null) : Day(test) {
    override fun solvePart1() = split(3)

    override fun solvePart2() = split(4)

    private fun split(part: Int): Long {
        val groupWeight = totalWeight / part
        var groupSize = Int.MAX_VALUE
        var groupEntanglement = Long.MAX_VALUE
        weights.subLists {
            if (it.sum() == groupWeight && it.size <= groupSize) {
                val entanglement = it.reduce { acc, i -> acc * i }
                if (entanglement < groupEntanglement) {
                    groupSize = it.size
                    groupEntanglement = entanglement
                }
            }
        }
        return groupEntanglement
    }

    private val weights = lines.map { it.toLong() }
    private val totalWeight = weights.sum()
}


