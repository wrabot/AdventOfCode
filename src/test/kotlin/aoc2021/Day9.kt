package aoc2021

import Day
import tools.board.toBoard
import tools.graph.zone

class Day9(test: Int? = null) : Day(test) {
    override fun solvePart1() = lowPoints.sumOf { board[it] + 1 }

    override fun solvePart2() = lowPoints.map { lowPoint ->
        zone(lowPoint) { xy -> board.neighbors4(xy).filter { board[it] != 9 } }.count()
    }.sortedDescending().take(3).reduce(Int::times)

    private val board = lines.toBoard { it.toString().toInt() }

    private val lowPoints = board.xy.filter { xy ->
        val height = board[xy]
        board.neighbors4(xy).all { board[it] > height }
    }
}
