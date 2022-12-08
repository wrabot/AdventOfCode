package aoc2022

import tools.Board
import tools.Day
import tools.Point

class Day8(test: Int? = null) : Day(2022, 8, test) {
    override fun solvePart1() = board.points.count { point ->
        val height = board[point]
        directions.any { point.isVisible(height, it) }
    }

    override fun solvePart2() = board.points.maxOf { point ->
        val height = board[point]
        directions.map { point.score(height, it, 0) }.reduce { acc, b -> acc * b }
    }

    private val directions = listOf(Point(0, -1), Point(0, 1), Point(1, 0), Point(-1, 0))

    private fun Point.isVisible(height: Int, direction: Point): Boolean = (this + direction).let { next ->
        board.getOrNull(next)?.let { it < height && next.isVisible(height, direction) } ?: true
    }

    private fun Point.score(height: Int, direction: Point, distance: Int): Int = (this + direction).let { next ->
        board.getOrNull(next)?.let {
            if (it < height) next.score(height, direction, distance + 1) else distance + 1
        } ?: distance
    }

    private val board = Board(lines[0].length, lines.size, lines.joinToString("").map { it - '0' })
}
