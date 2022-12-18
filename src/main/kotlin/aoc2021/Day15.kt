package aoc2021

import tools.Board
import tools.Day
import tools.shortPath

class Day15(test: Int? = null) : Day(2021, 15, test) {
    override fun solvePart1() =
        minRisk(Board(lines[0].length, lines.size, lines.flatMap { line -> line.map { Cell(it.toString().toInt()) } }))

    override fun solvePart2() = minRisk(
        Board(
            lines[0].length * 5,
            lines.size * 5,
            (0..4).flatMap { row ->
                lines.flatMap { line ->
                    (0..4).flatMap { column ->
                        line.map { Cell((it.toString().toInt() + column + row - 1) % 9 + 1) }
                    }
                }
            })
    )

    data class Cell(val risk: Int, var minRisk: Int = Int.MAX_VALUE) {
        override fun toString() = risk.toString()
    }

    private fun minRisk(cave: Board<Cell>) = shortPath(cave.points.first(), cave.points.last(), { cave[it].risk }) { cave.neighbors4(it) }.drop(1).sumOf { cave[it].risk }
}
