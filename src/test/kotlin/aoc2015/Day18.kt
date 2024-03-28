package aoc2015

import Day
import tools.board.Board
import tools.board.toBoard

class Day18(test: Int? = null) : Day(test) {
    override fun solvePart1(): Any {
        val board = lines.toBoard { Cell(it == '#') }
        repeat(100) { board.step() }
        return board.cells.count { it.current }
    }

    override fun solvePart2(): Any {
        val board = lines.toBoard { Cell(it == '#') }
        repeat(100) {
            board.forceCorners()
            board.step()
        }
        board.forceCorners()
        return board.cells.count { it.current }
    }

    private data class Cell(var current: Boolean, var next: Boolean = false) {
        override fun toString() = if (current) "#" else "."
    }

    private fun Board<Cell>.forceCorners() {
        this[0, 0].current = true
        this[width - 1, 0].current = true
        this[0, height - 1].current = true
        this[width - 1, height - 1].current = true
    }

    private fun Board<Cell>.step() {
        xy.forEach { xy ->
            when (neighbors8(xy).count { this[it].current }) {
                2 -> this[xy].next = this[xy].current
                3 -> this[xy].next = true
                else -> this[xy].next = false
            }
        }
        cells.forEach { it.current = it.next }
    }
}
