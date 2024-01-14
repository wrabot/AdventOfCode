package aoc2020

import Day
import tools.board.Board

class Day3 : Day() {
    override fun solvePart1() = slope(3, 1)

    override fun solvePart2() = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
        .map { slope(it.first, it.second) }
        .reduce { acc, i -> acc * i }

    private val map = Board(lines[0].length, lines.size, lines.flatMap { it.toList() })

    private fun slope(dx: Int, dy: Int) =
        (0 until map.height step dy).count { map[(it / dy * dx) % map.width, it] == '#' }
}
