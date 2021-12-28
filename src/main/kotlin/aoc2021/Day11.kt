package aoc2021

import tools.Board
import tools.Day

class Day11(test: Int? = null) : Day(2021, 11, test) {
    override fun getPart1() = part1
    override fun getPart2() = part2

    data class Cell(var level: Int) {
        constructor(init: Char) : this(init.toString().toInt())

        var flash = false
        override fun toString() = level.toString()
    }

    private val board = Board(lines[0].length, lines.size, lines.flatMap { it.map(::Cell) })

    private var flashes = 0
    private val part1: Int
    private val part2: Int

    init {
        repeat(100) { step() }
        part1 = flashes

        var step = 100
        while (board.cells.any { it.level != 0 }) {
            step()
            step++
        }
        part2 = step
    }

    private fun step() {
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
}
