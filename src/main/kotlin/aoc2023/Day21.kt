package aoc2023

import Day
import tools.board.Board
import tools.board.directions4
import tools.math.polynomial
import tools.math.polynomialCoefficients

class Day21(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        var tiles = setOf(garden.xy.first { garden[it].c == 'S' })
        repeat(64) { step ->
            tiles.forEach { garden[it].step = false }
            tiles = tiles.flatMap { point -> garden.neighbors4(point).filter { garden[it].c != '#' } }.toSet()
            tiles.forEach { garden[it].step = true }
        }
        return tiles.count()
    }

    override fun solvePart2(): Long {
        val target = 26501365
        val size = garden.width // input is square and intrinsic period is size !
        var tiles = setOf(garden.xy.first { garden[it].c == 'S' })
        repeat(target % size) { tiles = tiles.next() }
        return List(3) {
            if (it != 0) repeat(size) { tiles = tiles.next() }
            tiles.count().toDouble()
        }.polynomialCoefficients().polynomial((target / size - 2).toDouble()).toLong()
    }

    fun Set<Board.XY>.next() = flatMap { tile ->
        directions4.map { tile + it.delta }.filter { garden[it.mod()].c != '#' }
    }.toSet()

    private fun Board.XY.mod() = Board.XY(x.mod(garden.width), y.mod(garden.height))

    data class Cell(val c: Char) {
        var step = false
        override fun toString() = if (step) "O" else c.toString()
    }

    private val garden = Board(lines[0].length, lines.size, lines.flatMap { line -> line.map { Cell(it) } })
}
