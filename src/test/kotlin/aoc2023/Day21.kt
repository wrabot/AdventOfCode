package aoc2023

import Day
import tools.XY
import tools.board.Board
import tools.math.PolynomialInterpolation

class Day21(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        var tiles = setOf(garden.xy.first { garden[it].c == 'S' })
        repeat(64) {
            tiles.forEach { garden[it].step = false }
            tiles = tiles.flatMap { point -> garden.neighbors4(point).filter { garden[it].c != '#' } }.toSet()
            tiles.forEach { garden[it].step = true }
        }
        return tiles.count()
    }

    override fun solvePart2(): Long {
        val target = 26501365
        val periodicValuesSize = 3
        val size = garden.width // input is square and intrinsic period is size !
        var tiles = setOf(garden.xy.first { garden[it].c == 'S' })
        repeat(target % size) { tiles = tiles.next() }
        val periodicValues = List(periodicValuesSize) {
            if (it != 0) repeat(size) { tiles = tiles.next() }
            tiles.count().toDouble()
        }
        return PolynomialInterpolation(periodicValues)((target / size - periodicValuesSize + 1).toDouble()).toLong()
    }

    fun Set<XY>.next() = flatMap { tile ->
        XY.xy4dir.map { tile + it }.filter { garden[it.mod()].c != '#' }
    }.toSet()

    private fun XY.mod() = XY(x.mod(garden.width), y.mod(garden.height))

    private data class Cell(val c: Char) {
        var step = false
        override fun toString() = if (step) "O" else c.toString()
    }

    private val garden = Board(lines[0].length, lines.size, lines.flatMap { line -> line.map { Cell(it) } })
}
