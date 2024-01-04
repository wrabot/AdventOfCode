package aoc2015

import tools.board.Board
import Day

class Day18(test: Int? = null) : Day(2015, 18, test) {
    override fun solvePart1(): Any {
        val board = Board(lines[0].length, lines.size, lines.flatMap { line -> line.map { Cell(it == '#') } })
        repeat(100) { board.step() }
        return board.cells.count { it.current }
    }

    override fun solvePart2(): Any {
        val board = Board(lines[0].length, lines.size, lines.flatMap { line -> line.map { Cell(it == '#') } })
        repeat(100) {
            board.forceCorners()
            board.step()
        }
        board.forceCorners()
        return board.cells.count { it.current }
    }

    data class Cell(var current: Boolean, var next: Boolean = false) {
        override fun toString() = if (current) "#" else "."
    }

    private fun Board<Cell>.forceCorners() {
        this[0, 0].current = true
        this[width - 1, 0].current = true
        this[0, height - 1].current = true
        this[width - 1, height - 1].current = true
    }

    private fun Board<Cell>.step() {
        points.forEach { point ->
            when (neighbors8(point).count { this[it].current }) {
                2 -> this[point].next = this[point].current
                3 -> this[point].next = true
                else -> this[point].next = false
            }
        }
        cells.forEach { it.current = it.next }
    }
}
