package aoc2021

import Day

class Day6(test: Int? = null) : Day(2021, 6, test) {
    override fun solvePart1(): Any {
        lines[0].split(",").map { generation[it.toInt()]++ }
        repeat(80) { step() }
        return generation.sum()
    }

    override fun solvePart2(): Any {
        part1 // force part1
        repeat(256 - 80) {  step() }
        return generation.sum()
    }

    private var generation = LongArray(9) { 0 }

    private fun step() {
        generation = LongArray(9) { timer ->
            when (timer) {
                8 -> generation[0]
                6 -> generation[0] + generation[7]
                else -> generation[timer + 1]
            }
        }
    }
}
