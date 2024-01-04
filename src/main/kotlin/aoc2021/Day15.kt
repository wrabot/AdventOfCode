package aoc2021

import tools.Day
import tools.board.Board
import tools.graph.shortPath

class Day15(test: Int? = null) : Day(2021, 15, test) {
    override fun solvePart1() =
        minRisk(
            Board(
                lines[0].length,
                lines.size,
                lines.flatMap { line -> line.map { Cell(it.toString().toDouble()) } })
        )

    override fun solvePart2() = minRisk(
        Board(
            lines[0].length * 5,
            lines.size * 5,
            (0..4).flatMap { row ->
                lines.flatMap { line ->
                    (0..4).flatMap { column ->
                        line.map { Cell((it.toString().toDouble() + column + row - 1) % 9 + 1) }
                    }
                }
            })
    )

    data class Cell(val risk: Double) {
        override fun toString() = risk.toString()
    }

    private fun minRisk(cave: Board<Cell>) = shortPath(
        cave.points.first(),
        cave.points.last(),
        { _, destination -> cave[destination].risk }
    ) { cave.neighbors4(it) }.drop(1).sumOf { cave[it].risk }.toInt()
}
