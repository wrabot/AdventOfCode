package aoc2021

import tools.Board
import tools.Day

class Day9(test: Int? = null) : Day(2021, 9, test) {
    override fun solvePart1() = lowPoints.sumOf { board[it] + 1 }

    override fun solvePart2() = lowPoints.map { lowPoint -> board.zone4(lowPoint) { board[it] != 9 }.count() }
        .sortedDescending().take(3).reduce(Int::times)

    private val board = Board(lines[0].length, lines.size, lines.flatMap { line -> line.map { it.toString().toInt() } })

    private val lowPoints = board.points.filter { point ->
        val height = board[point]
        board.neighbors4(point).all { board[it] > height }
    }
}
