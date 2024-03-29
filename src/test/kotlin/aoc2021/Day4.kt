package aoc2021

import Day
import tools.board.Board

class Day4(test: Int? = null) : Day(test) {
    override fun solvePart1() = firstWinningScore
    override fun solvePart2() = lastWinningScore

    private data class Cell(val value: Int, var marked: Boolean = false)

    private val numbers = lines[0].split(",").map { it.toInt() }

    private val boards = lines.drop(1).chunked(6).associate { board ->
        val rows = board.drop(1).map { row -> row.chunked(3).map { Cell(it.trim().toInt()) } }
        val columns = (0 until 5).map { column -> rows.map { it[column] } }
        Board(5, 5, rows.flatten()) to (rows + columns)
    }

    private val firstWinningScore: Int
    private val lastWinningScore: Int

    init {
        var first = 0
        var last = 0
        val completeBoards = mutableSetOf<Board<Cell>>()
        numbers.forEach { number ->
            boards.forEach { board ->
                board.key.cells.forEach { if (it.value == number) it.marked = true }
                if (board.value.any { line -> line.all { it.marked } }) {
                    val score = board.key.cells.sumOf { if (it.marked) 0 else it.value } * number
                    if (completeBoards.isEmpty()) first = score
                    if (completeBoards.size == boards.size - 1 && board.key !in completeBoards) last = score
                    completeBoards.add(board.key)
                }
            }
        }
        firstWinningScore = first
        lastWinningScore = last
    }
}
