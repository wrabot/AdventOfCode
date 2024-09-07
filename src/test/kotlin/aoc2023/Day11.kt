package aoc2023

import Day
import tools.board.Board
import tools.XY
import tools.range.rangeMinMax

class Day11(test: Int? = null) : Day(test) {
    override fun solvePart1() = solve(2)

    override fun solvePart2() = solve(1000000)

    private fun solve(factor: Long) = galaxies.sumOf { a ->
        galaxies.sumOf { b ->
            distance(a, b, factor)
        }
    } / 2

    fun distance(a: XY, b: XY, factor: Long) =
        (a - b).manhattan() + (factor - 1) * (emptyColumn.emptyCount(a.x, b.x) + emptyRows.emptyCount(a.y, b.y))

    private fun List<Int>.emptyCount(a: Int, b: Int) = count { it in rangeMinMax(a, b) }

    private val universe = Board(lines[0].length, lines.size, lines.flatMap { it.toList() })
    private val galaxies = universe.xy.filter { universe[it] == '#' }

    private val emptyRows = (0..<universe.height).filter { y ->
        (0..<universe.width).all { universe[it, y] == '.' }
    }
    private val emptyColumn = (0..<universe.width).filter { x ->
        (0..<universe.height).all { universe[x, it] == '.' }
    }
}
