package aoc2018

import Day
import tools.board.Board
import tools.XY
import tools.text.match

class Day3(test: Int? = null) : Day(test) {
    override fun solvePart1() = board.cells.count { it.count >= 2 }

    override fun solvePart2() = rects.first { rect -> rect.points.all { board[it].count == 1 } }.id

    private data class Cell(var count: Int)
    private data class Rect(val id: Int, val topLeft: XY, val size: XY) {
        val points = (topLeft.y until topLeft.y + size.y).flatMap { y ->
            (topLeft.x until topLeft.x + size.x).map { x ->
                XY(x, y)
            }
        }
    }

    private val size = 1000
    private val board = Board(size, size, List(size * size) { Cell(0) })
    private val regex = "#(.*) @ (.*),(.*): (.*)x(.*)".toRegex()
    private val rects = lines.map { line ->
        val (id, x, y, w, h) = line.match(regex)!!.map { it.toInt() }
        Rect(id, XY(x, y), XY(w, h))
    }

    init {
        rects.forEach { rect -> rect.points.forEach { board[it].count++ } }
    }
}
