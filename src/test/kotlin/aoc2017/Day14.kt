package aoc2017

import Day
import tools.board.Board
import tools.graph.zone
import java.math.BigInteger

class Day14(test: Int? = null) : Day(test) {
    override fun solvePart1() = grid.sumOf { it.bitCount() }
    override fun solvePart2() = board.xy.filter { board[it] }.map {
        zone(it) { xy -> board.neighbors4(xy).filter { board[it] } }.toSet()
    }.toSet().count()

    private val grid = (0..127).map { BigInteger(knotHash("$input-$it"), 16) }
    private val board = Board(128, 128, grid.flatMap { row -> (0..127).map { row.testBit(it) } })
}
