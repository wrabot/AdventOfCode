package aoc2016

import Day
import tools.board.XY
import tools.board.Direction4
import tools.graph.shortPath
import java.math.BigInteger

class Day13(test: Int? = null) : Day(test) {
    private val start = XY(1, 1)

    override fun solvePart1() = shortPath(start, XY(lines[1].toInt(), lines[2].toInt())) { neighbors(it) }.size - 1

    override fun solvePart2() = find(setOf(start), emptySet(), 50).size

    fun find(todo: Set<XY>, found: Set<XY>, level: Int): Set<XY> = if (level == 0) found else
        todo.flatMap { neighbors(it) }.filter { it !in found }.toSet().let { find(it, found + it, level - 1) }

    private val number = lines[0].toInt()
    private fun neighbors(xy: XY) = Direction4.entries.map { xy + it.xy }.filter {
        it.run {
            x >= 0 && y >= 0 && BigInteger.valueOf(x * x + 3L * x + 2 * x * y + y + y * y + number).bitCount() % 2 == 0
        }
    }
}
