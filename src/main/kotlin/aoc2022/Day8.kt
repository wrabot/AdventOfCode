package aoc2022

import Day
import tools.board.Board

class Day8(test: Int? = null) : Day(test) {
    override fun solvePart1() = board.xy.count { xy ->
        val height = board[xy]
        board.directions4.any { xy.isVisible(height, it) }
    }

    override fun solvePart2() = board.xy.maxOf { point ->
        val height = board[point]
        board.directions4.map { point.score(height, it, 0) }.reduce { acc, b -> acc * b }
    }

    private fun Board.XY.isVisible(height: Int, direction: Board.XY): Boolean = (this + direction).let { next ->
        board.getOrNull(next)?.let { it < height && next.isVisible(height, direction) } ?: true
    }

    private fun Board.XY.score(height: Int, direction: Board.XY, distance: Int): Int = (this + direction).let { next ->
        board.getOrNull(next)?.let {
            if (it < height) next.score(height, direction, distance + 1) else distance + 1
        } ?: distance
    }

    private val board = Board(lines[0].length, lines.size, lines.joinToString("").map { it - '0' })
}
