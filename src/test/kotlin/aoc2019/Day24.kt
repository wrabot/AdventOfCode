package aoc2019

import Day
import tools.board.Board
import tools.board.toBoard
import tools.log

class Day24(var test: Int? = null) : Day(test) {
    override fun solvePart1(): Long {
        var current = lines.toBoard { it }
        val previous = mutableSetOf(current.toString())
        while (true) {
            current = with(current) {
                Board(width, height, cells.indices.map { index ->
                    when (neighbors4(xy[index]).count { this[it] == '#' }) {
                        1 -> '#'
                        2 -> if (cells[index] == '.') '#' else '.'
                        else -> '.'
                    }
                })
            }
            if (!previous.add(current.toString())) return current.cells.foldIndexed(0L) { index, acc, c ->  
                if (c == '#') acc + (1L shl index) else acc
            }
        }
    }

    override fun solvePart2() {
        var current = lines.toBoard { it }
    }
}
