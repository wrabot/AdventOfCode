package aoc2021

import Day
import tools.board.Board
import tools.board.toBoard
import tools.graph.shortPath

class Day15(test: Int? = null) : Day(test) {
    override fun solvePart1() = minRisk(lines.toBoard { Cell(it.toString().toDouble()) })

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
        cave.xy.first(),
        cave.xy.last(),
        { _, destination -> cave[destination].risk }
    ) { cave.neighbors4(it) }.drop(1).sumOf { cave[it].risk }.toInt()
}
