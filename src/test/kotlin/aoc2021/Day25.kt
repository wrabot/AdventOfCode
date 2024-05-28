package aoc2021

import Day
import tools.board.Board
import tools.board.XY
import tools.board.toBoard

class Day25(test: Int? = null) : Day(test) {
    override fun solvePart1(): Any {
        val sea = lines.toBoard(::Cell)
        var step = 0
        do {
            val east = sea.move('>', 1, 0)
            val south = sea.move('v', 0, 1)
            step++
        } while (east || south)
        return step
    }

    override fun solvePart2() = Unit

    private data class Cell(var content: Char) {
        override fun toString() = content.toString()
    }

    private fun Board<Cell>.move(c: Char, dx: Int, dy: Int): Boolean {
        val moves = xy.filter { this[it].content == c }.mapNotNull {
            val next = XY((it.x + dx) % width, (it.y + dy) % height)
            if (this[next].content == '.') it to next else null
        }
        moves.forEach {
            this[it.first].content = '.'
            this[it.second].content = c
        }
        return moves.isNotEmpty()
    }
}
