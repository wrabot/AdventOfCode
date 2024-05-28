package aoc2023

import Day
import tools.board.Board
import tools.board.Direction4
import tools.board.XY

class Day23(test: Int? = null) : Day(test) {
    override fun solvePart1() = findPathMaxSizePart1()

    private fun findPathMaxSizePart1(path: List<XY> = listOf(start)): Int {
        val last = path.last()
        if (last == end) return path.size - 1
        return Direction4.entries.maxOf {
            val point = last + it.xy
            val c = map.getOrNull(point) ?: return@maxOf 0
            if (c != '.' && c != it.c) return@maxOf 0
            if (point in path) return@maxOf 0
            findPathMaxSizePart1(path + point)
        }
    }

    override fun solvePart2() = reducedMap().findPathMaxSizePart2(listOf(start), 0)

    private fun XY.neighbors() = map.neighbors4(this).filter { map[it] != '#' }

    private fun reducedMap() = map.xy.filter { map[it] != '#' }.flatMap { intersection ->
        intersection.neighbors().takeIf { it.size != 2 }.orEmpty().map { first ->
            generateSequence(first to intersection) { (current, previous) ->
                current.neighbors().singleOrNull { it != previous }?.to(current)
            }
        }
    }.groupBy({ it.first().second }, { Pair(it.last().first, it.count()) })

    private fun Map<XY, List<Pair<XY, Int>>>.findPathMaxSizePart2(path: List<XY>, size: Int): Int =
        if (path.last() == end) size else getValue(path.last()).filter { it.first !in path }
            .maxOfOrNull { findPathMaxSizePart2(path + it.first, size + it.second) } ?: 0

    private val map = Board(lines[0].length, lines.size, lines.flatMap { it.toList() })
    private val start = map.xy.first { map[it] == '.' }
    private val end = map.xy.last { map[it] == '.' }
}
