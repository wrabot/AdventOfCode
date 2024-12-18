package aoc2024

import Day
import tools.XY
import tools.board.Board
import tools.board.CharCell
import tools.graph.shortPath
import tools.toXY

class Day18(test: Int? = null) : Day(test) {
    private val size = lines[0].toInt()
    private val cut = lines[1].toInt()
    private val corrupted = lines.drop(2).map { it.toXY(",") }
    private val start = XY(0, 0)
    private val exit = XY(size - 1, size - 1)
    private val map = Board(size, size, List(size * size) { CharCell('.') })

    init {
        corrupted.take(cut).forEach { map[it].c = '#' }
    }

    private fun findExit() = shortPath(start, exit) { xy ->
        map.neighbors4(xy).filter { map.getOrNull(it)?.c == '.' }
    }

    override fun solvePart1() = findExit().size - 1

    override fun solvePart2() = corrupted.drop(cut).first {
        map[it].c = '#'
        findExit().isEmpty()
    }.run { "$x,$y" }
}
