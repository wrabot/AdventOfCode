package aoc2021

import Day
import tools.board.Board

class Day11(test: Int? = null) : Day(test) {
    override fun solvePart1(): Any {
        repeat(100) { step() }
        return flashes
    }

    override fun solvePart2(): Any {
        part1 // force part1
        var step = 100
        while (board.cells.any { it.level != 0 }) {
            step()
            step++
        }
        return step
    }

    private data class Cell(var level: Int) {
        constructor(init: Char) : this(init.toString().toInt())

        var flash = false
        override fun toString() = level.toString()
    }

    private val board = Board(lines[0].length, lines.size, lines.flatMap { it.map(::Cell) })

    private var flashes = 0

    private fun step() {
        board.cells.forEach { it.level++ }
        do {
            var flash = false
            board.xy.forEach { xy ->
                val cell = board[xy]
                if (!cell.flash && cell.level > 9) {
                    cell.flash = true
                    flash = true
                    flashes++
                    board.neighbors8(xy).forEach { board[it].level++ }
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
}
