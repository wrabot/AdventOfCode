package aoc2018

import Day
import tools.XY
import tools.board.Direction4

class Day20(test: Int? = null) : Day(test) {
    override fun solvePart1() = distances.values.max()
    override fun solvePart2() = distances.values.count { it >= 1000 }

    private val distances = mutableMapOf(XY(0, 0) to 0)

    init {
        input.drop(1).dropLast(1).parse()
    }

    private fun String.parse(offset: Int = 0, current: XY = XY(0, 0)): Pair<Set<XY>, Int> {
        val (xy, start) = parseDirections(current, offset)
        if (getOrNull(start) != '(') return Pair(setOf(xy), start)
        val set = mutableSetOf<XY>()
        var i = start + 1
        do {
            val (list, next) = parse(i, xy)
            set.addAll(list)
            val c = get(next)
            i = next + 1
        } while (c != ')')
        return set.map { parse(i, it) }.run { flatMap { it.first }.toSet() to map { it.second }.distinct().single() }
    }

    private fun String.parseDirections(current: XY, offset: Int): Pair<XY, Int> {
        var xy = current
        for (i in offset until length) {
            val next = xy.move(get(i)) ?: return xy to i
            distances.putIfAbsent(next, distances[xy]!! + 1)
            xy = next
        }
        return xy to length
    }

    private fun XY.move(c: Char) = when (c) {
        'N' -> Direction4.North.xy
        'S' -> Direction4.South.xy
        'W' -> Direction4.West.xy
        'E' -> Direction4.East.xy
        else -> null
    }?.let { this + it }
}
