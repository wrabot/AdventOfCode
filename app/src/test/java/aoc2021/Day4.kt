package aoc2021

import Board
import forEachInput
import log

object Day4 {
    fun solve() = forEachInput(2021, 4, 1, 2) { lines ->
        val numbers = lines[0].split(",").map { it.toInt() }

        val boards = lines.drop(1).chunked(6).map { board ->
            val rows = board.drop(1).map { row -> row.chunked(3).map { Cell(it.trim().toInt()) } }
            val columns = (0 until 5).map { column -> rows.map { it[column] } }
            Board(5, 5, rows.flatten()) to (rows + columns)
        }.toMap()

        val completeBoards = mutableSetOf<Board<Cell>>()
        numbers.forEach { number ->
            boards.forEach { board ->
                board.key.cells.forEach { if (it.value == number) it.marked = true }
                if (board.value.any { line -> line.all { it.marked } }) {
                    val score = board.key.cells.sumOf { if (it.marked) 0 else it.value } * number
                    if (completeBoards.isEmpty()) {
                        log("part 1: ")
                        score.log()
                    }
                    if (completeBoards.size == boards.size - 1 && board.key !in completeBoards) {
                        log("part 2: ")
                        score.log()
                    }
                    completeBoards.add(board.key)
                }
            }
        }
    }

    data class Cell(val value: Int, var marked: Boolean = false)
}
