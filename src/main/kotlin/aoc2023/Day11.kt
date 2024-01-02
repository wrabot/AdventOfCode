package aoc2023

import tools.board.Board
import tools.Day
import tools.board.Point
import tools.range.rangeMinMax

class Day11(test: Int? = null) : Day(2023, 11, test) {
    override fun solvePart1() = solve(2)

    override fun solvePart2() = solve(1000000)

    private fun solve(factor: Long) = galaxies.sumOf { a ->
        galaxies.sumOf { b ->
            distance(a, b, factor)
        }
    } / 2

    fun distance(a: Point, b: Point, factor: Long) =
        a.distance(b) + (factor - 1) * (emptyColumn.emptyCount(a.x, b.x) + emptyRows.emptyCount(a.y, b.y))

    private fun List<Int>.emptyCount(a: Int, b: Int) = count { it in rangeMinMax(a, b) }

    private val universe = Board(lines[0].length, lines.size, lines.flatMap { it.toList() })
    private val galaxies = universe.points.filter { universe[it] == '#' }

    private val emptyRows = (0..<universe.height).filter { y ->
        (0..<universe.width).all { universe[it, y] == '.' }
    }
    private val emptyColumn = (0..<universe.width).filter { x ->
        (0..<universe.height).all { universe[x, it] == '.' }
    }
}
