package aoc2021

import tools.Board
import tools.Point
import forEachInput
import tools.log

object Day25 {
    fun solve() = forEachInput(2021, 25, 1, 2) { lines ->
        val sea = Board(lines[0].length, lines.size, lines.flatMap { line -> line.map { Cell(it) } })
        var step = 0
        do {
            val east = sea.move('>', 1, 0)
            val south = sea.move('v', 0, 1)
            step++
        } while (east || south)
        step.log()
    }

    data class Cell(var content: Char) {
        override fun toString() = content.toString()
    }

    private fun Board<Cell>.move(c: Char, dx: Int, dy: Int): Boolean {
        val moves = points.filter { this[it].content == c }.mapNotNull {
            val next = Point((it.x + dx) % width, (it.y + dy) % height)
            if (this[next].content == '.') it to next else null
        }
        moves.forEach {
            this[it.first].content = '.'
            this[it.second].content = c
        }
        return moves.isNotEmpty()
    }
}
