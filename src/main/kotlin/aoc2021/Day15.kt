package aoc2021

import tools.Board
import tools.Day

class Day15(test: Int? = null) : Day(2021, 15, test) {
    override fun getPart1() =
        minRisk(Board(lines[0].length, lines.size, lines.flatMap { line -> line.map { Cell(it.toString().toInt()) } }))

    override fun getPart2() = minRisk(
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

    private fun minRisk(cave: Board<Cell>): Int {
        val start = cave.points.first()
        val end = cave.points.last()
        cave[start].minRisk = 0

        val todo = mutableListOf(start)
        while (todo.isNotEmpty()) {
            val point = todo.removeAt(0)
            val minRisk = cave[point].minRisk
            cave.neighbors4(point).forEach {
                val neighbor = cave[it]
                val newRisk = neighbor.risk + minRisk
                if (newRisk < neighbor.minRisk) {
                    neighbor.minRisk = newRisk
                    if (it !in todo) todo.add(it)
                }
            }
            todo.sortBy { cave[it].minRisk }
        }
        return cave[end].minRisk
    }
}
