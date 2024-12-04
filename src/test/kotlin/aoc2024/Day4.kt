package aoc2024

import Day
import tools.board.Direction8
import tools.board.Direction8.*
import tools.board.toBoard

class Day4(test: Int? = null) : Day(test) {
    private val board = lines.toBoard { it }
    private val xmasPattern = "XMAS".toList()
    private val diagonals = listOf(NorthWest, NorthEast, SouthEast, SouthWest)
    private val msPatterns = listOf(
        listOf('M', 'M', 'S', 'S'),
        listOf('M', 'S', 'S', 'M'),
        listOf('S', 'S', 'M', 'M'),
        listOf('S', 'M', 'M', 'S'),
    )

    override fun solvePart1() = board.xy.sumOf { p ->
        Direction8.entries.count { d ->
            xmasPattern.indices.mapNotNull { board.getOrNull(p + d.xy * it) } == xmasPattern
        }
    }

    override fun solvePart2() = board.xy.filter { board[it] == 'A' }.count { p ->
        diagonals.mapNotNull { board.getOrNull(p + it.xy) } in msPatterns
    }
}
