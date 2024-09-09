package aoc2022

import Day
import tools.board.Board
import tools.XY

class Day8(test: Int? = null) : Day(test) {
    override fun solvePart1() = board.xy.count { xy ->
        val height = board[xy]
        XY.xy4dir.any { xy.isVisible(height, it) }
    }

    override fun solvePart2() = board.xy.maxOf { xy ->
        val height = board[xy]
        XY.xy4dir.map { xy.score(height, it, 0) }.reduce { acc, b -> acc * b }
    }

    private fun XY.isVisible(height: Int, direction: XY): Boolean = (this + direction).let { next ->
        board.getOrNull(next)?.let { it < height && next.isVisible(height, direction) } ?: true
    }

    private fun XY.score(height: Int, direction: XY, distance: Int): Int = (this + direction).let { next ->
        board.getOrNull(next)?.let {
            if (it < height) next.score(height, direction, distance + 1) else distance + 1
        } ?: distance
    }

    private val board = Board(lines[0].length, lines.size, lines.joinToString("").map { it - '0' })
}
