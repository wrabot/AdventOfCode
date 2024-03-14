package aoc2017

import Day

class Day10(test: Int? = null) : Day(test) {
    override fun solvePart1() = MutableList(256) { it }.apply {
        knotHashRound(input.split(",").map { it.toInt() }, 1)
    }.let { it[0] * it[1] }

    override fun solvePart2() = knotHash(input)
}
