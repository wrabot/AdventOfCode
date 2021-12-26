package aoc2021

import Board
import forEachInput
import log

object Day11 {
    fun solve() = forEachInput(2021, 11, 1, 2) { lines ->
        data class Cell(var level: Int) {
            var flash = false
            override fun toString() = level.toString()
        }

        val board = Board(lines[0].length, lines.size, lines.flatMap { line -> line.map { Cell(it.toString().toInt()) } })

        var flashes = 0
        fun step() {
            board.cells.forEach { it.level++ }
            do {
                var flash = false
                board.points.forEach { point ->
                    val cell = board[point]
                    if (!cell.flash && cell.level > 9) {
                        cell.flash = true
                        flash = true
                        flashes++
                        board.neighbors8(point).forEach { board[it].level++ }
                    }
                }
            } while (flash)
            board.cells.forEach {
                if (it.flash) {
                    it.flash = false
                    it.level = 0
                }
            }
        }

        log("part 1: ")
        repeat(100) { step() }
        flashes.log()

        log("part 2: ")
        var step = 100
        while (board.cells.any { it.level != 0 }) {
            step()
            step++
        }
        step.log()
    }
}
