package aoc2018

import Day
import tools.XY
import tools.board.Board
import tools.toXY

class Day6(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        var todo = locations
        todo.forEachIndexed { index, xy ->
            board[xy].i = index
            board[xy].d = 0
        }
        var d = 1
        while (todo.isNotEmpty()) {
            val new = mutableSetOf<XY>()
            todo.forEach {
                val index = board[it].i
                board.neighbors4(it).forEach { neighbor ->
                    val cell = board[neighbor]
                    when {
                        cell.i < 0 -> {
                            cell.i = index
                            cell.d = d
                            new.add(neighbor)
                        }

                        cell.i != index && cell.d == d -> {
                            cell.i = Int.MAX_VALUE
                            new.remove(neighbor)
                        }
                    }
                }
            }
            todo = new
            d++
        }
        val borders = board.xRange.flatMap { listOf(board[it, 0].i, board[it, height - 1].i) } +
                board.yRange.flatMap { listOf(board[0, it].i, board[width - 1, it].i) }
        return board.xy.groupBy { board[it].i }.filter { it.key !in borders }.maxOf { it.value.size }
    }

    override fun solvePart2() = board.xy.count { l -> locations.sumOf { (it - l).manhattan() } < 10000 }

    private data class Cell(var i: Int = -1, var d: Int = Int.MAX_VALUE) {
        override fun toString() = when (i) {
            -1 -> "!"
            Int.MAX_VALUE -> "."
            else -> "#"
        }
    }

    private val points = lines.map { it.toXY(", ") }
    private val offset = XY(points.minOf { it.x } - 1, points.minOf { it.y } - 1)
    private val locations = points.map { it - offset }.toSet()
    private val width = points.maxOf { it.x } - offset.x + 2
    private val height = points.maxOf { it.y } - offset.y + 2
    private val board = Board(width, height, List(width * height) { Cell() })
}
