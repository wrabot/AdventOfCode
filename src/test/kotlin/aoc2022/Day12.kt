package aoc2022

import Day
import tools.board.Board

class Day12(test: Int? = null) : Day(test) {
    override fun solvePart1() = board[start].distance
    override fun solvePart2() = board.cells.filter { it.height == 0 }.minOf { it.distance }

    private data class Cell(val content: Char, val height: Int, var distance: Int)

    private val board = Board(lines[0].length, lines.size, lines.joinToString("").toList().map {
        val height = when (it) {
            'S' -> 'a'
            'E' -> 'z'
            else -> it
        } - 'a'
        Cell(content = it, height = height, distance = Int.MAX_VALUE)
    })
    private val start = board.xy.first { board[it].content == 'S' }
    private val end = board.xy.first { board[it].content == 'E' }

    init {
        board[end].distance = 0
        var points = listOf(end)
        while (points.isNotEmpty()) {
            points = points.computeDistance()
        }
    }

    private fun List<Board.XY>.computeDistance(): List<Board.XY> = flatMap {
        val cell = board[it]
        val distance = cell.distance + 1
        board.neighbors4(it).mapNotNull { point ->
            val neighbor = board[point]
            if (neighbor.distance <= distance || cell.height !in 0..(neighbor.height + 1)) null else {
                neighbor.distance = distance
                point
            }
        }
    }
}
