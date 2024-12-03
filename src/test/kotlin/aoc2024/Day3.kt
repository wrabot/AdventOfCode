package aoc2024

import Day

class Day3(test: Int? = null) : Day(test) {
    override fun solvePart1() = input.parse()

    override fun solvePart2() = "do $input".split("don't").sumOf {
        it.split("do", limit = 2).getOrNull(1).orEmpty().parse()
    }

    private val regex = Regex("mul\\((\\d+),(\\d+)\\)")
    private fun String.parse() = regex.findAll(this).sumOf { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
}
