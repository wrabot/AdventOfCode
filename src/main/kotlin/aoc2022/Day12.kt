package aoc2022

import tools.board.Board
import tools.Day
import tools.board.Point

class Day12(test: Int? = null) : Day(2022, 12, test) {
    override fun solvePart1() = board[start].distance
    override fun solvePart2() = board.cells.filter { it.height == 0 }.minOf { it.distance }

    data class Cell(val content: Char, val height: Int, var distance: Int)

    val board = Board(lines[0].length, lines.size, lines.joinToString("").toList().map {
        val height = when (it) {
            'S' -> 'a'
            'E' -> 'z'
            else -> it
        } - 'a'
        Cell(content = it, height = height, distance = Int.MAX_VALUE)
    })
    val start = board.points.first { board[it].content == 'S' }
    val end = board.points.first { board[it].content == 'E' }

    init {
        board[end].distance = 0
        var points = listOf(end)
        while (points.isNotEmpty()) {
            points = points.computeDistance()
        }
    }

    private fun List<Point>.computeDistance(): List<Point> = flatMap {
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
