package aoc2018

import Day
import tools.board.Board
import tools.log

class Day11(test: Int? = null) : Day(test) {
    override fun solvePart1() = power3.xy.maxBy { power3[it] }.run { "$x,$y" }

    override fun solvePart2() = (1..grid.width).map { size ->
        val g = cache.getOrPut(size) { power(size) }
        val xy = g.xy.maxBy { g[it] }
        Triple(size, xy, g[xy])
    }.maxBy { it.third }.run { "${second.x},${second.y},$first" }

    private fun power(size: Int) = Board(grid.width - size + 1, grid.height - size + 1) { x, y ->
        grid[x, y] + (1..<size).sumOf { grid[x + it, y] + grid[x, y + it] } + cache[size - 1]!![x + 1, y + 1]
    }

    private val serial = input.toInt()
    private val grid = Board(300, 300) { x, y ->
        val rackID = x + 10
        ((rackID * y + serial) * rackID) / 100 % 10 - 5
    }
    private val power3 = Board(grid.width - 2, grid.height - 2) { x, y ->
        (0..2).sumOf { r -> (0..2).sumOf { c -> grid[x + c, y + r] } }
    }
    private val cache = mutableMapOf(1 to grid)
}
