package aoc2022

import tools.Day

class Day2(test: Int? = null) : Day(2022, 2, test) {
    override fun solvePart1() = guide.sumOf { it.score() }
    override fun solvePart2() = guide.map { it.first to (it.first + it.second + 2) % 3 }.sumOf { it.score() }
    private fun Pair<Int, Int>.score() = ((4 + second - first) % 3) * 3 + 1 + second
    private val guide = lines.map { (it[0] - 'A') to (it[2] - 'X') }
}
