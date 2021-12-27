package aoc2021

import tools.Board
import forEachInput
import tools.log

object Day9 {
    fun solve() = forEachInput(2021, 9, 1, 2) { lines ->
        val board = Board(lines[0].length, lines.size, lines.flatMap { line -> line.map { it.toString().toInt() } })

        val lowPoints = board.points.filter { point ->
            val height = board[point]
            board.neighbors4(point).all { board[it] > height }
        }

        log("part 1: ")
        lowPoints.sumOf { board[it] + 1 }.log()

        log("part 2: ")
        lowPoints.map { lowPoint -> board.zone4(lowPoint) { board[it] != 9 }.count() }
            .sortedDescending().take(3).reduce(Int::times).log()
    }
}
