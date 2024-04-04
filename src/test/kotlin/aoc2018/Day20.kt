package aoc2018

import Day
import tools.board.Board.XY
import tools.board.Direction4

class Day20(test: Int? = null) : Day(test) {
    override fun solvePart1() = distances.values.max()
    override fun solvePart2() = distances.values.count { it >= 1000 }

    private val distances = mutableMapOf(XY(0, 0) to 0)

    init {
        input.drop(1).dropLast(1).parse()
    }

    private fun String.parse(offset: Int = 0, last: XY = XY(0, 0)): Pair<Set<XY>, Int> {
        return when (val c = getOrNull(offset) ?: return Pair(setOf(last), offset)) {
            '(' -> {
                var i = offset + 1
                val set = mutableSetOf<XY>()
                do {
                    val (l, n) = parse(i, last)
                    set.addAll(l)
                    i = n + 1
                } while (get(n) == '|')
                set.map { parse(i, it) }.run { flatMap { it.first }.toSet() to map { it.second }.distinct().single() }
            }

            '|', ')' -> return setOf(last) to offset
            else -> last.move(c).let {
                distances.putIfAbsent(it, distances[last]!! + 1)
                parse(offset + 1, it)
            }
        }
    }

    private fun XY.move(c: Char) = this + when (c) {
        'N' -> Direction4.North.xy
        'S' -> Direction4.South.xy
        'W' -> Direction4.West.xy
        'E' -> Direction4.East.xy
        else -> error("!!!")
    }
}
