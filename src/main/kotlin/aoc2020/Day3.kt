package aoc2020

import tools.Day

class Day3 : Day(2020, 3) {
    override fun solvePart1() = map.slope(width, height, 3, 1)

    override fun solvePart2() = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
        .map { map.slope(width, height, it.first, it.second) }
        .reduce { acc, i -> acc * i }

    //TODO use board ?
    private val width = lines[0].length
    private val height = lines.size
    private val map = lines.joinToString("")

    private fun String.slope(width: Int, height: Int, dx: Int, dy: Int) =
        (0 until height step dy).count { this[it * width + (it / dy * dx) % width] == '#' }
}
