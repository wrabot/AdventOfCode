package aoc2023

import tools.Board
import tools.Day
import tools.Direction
import tools.Point

class Day23(test: Int? = null) : Day(2023, 23, test) {
    override fun solvePart1() = findPathMaxSizePart1()

    private fun findPathMaxSizePart1(path: List<Point> = listOf(start)): Int {
        val last = path.last()
        if (last == end) return path.size - 1
        return directions.maxOf {
            val point = last + it.delta
            val c = map.getOrNull(point) ?: return@maxOf 0
            if (c != '.' && c != it.c) return@maxOf 0
            if (point in path) return@maxOf 0
            findPathMaxSizePart1(path + point)
        }
    }

    override fun solvePart2() = reducedMap().findPathMaxSizePart2(listOf(start), 0)

    private fun Point.neighbors() = map.neighbors4(this).filter { map[it] != '#' }

    private fun reducedMap() = map.points.filter { map[it] != '#' }.flatMap { intersection ->
        intersection.neighbors().takeIf { it.size != 2 }.orEmpty().map { first ->
            generateSequence(first to intersection) { (current, previous) ->
                current.neighbors().singleOrNull { it != previous }?.to(current)
            }
        }
    }.groupBy({ it.first().second }, { Pair(it.last().first, it.count()) })

    private fun Map<Point, List<Pair<Point, Int>>>.findPathMaxSizePart2(path: List<Point>, size: Int): Int =
        if (path.last() == end) size else getValue(path.last()).filter { it.first !in path }
            .maxOfOrNull { findPathMaxSizePart2(path + it.first, size + it.second) } ?: 0

    private val directions = Direction.entries.filterNot { it.isDiadonal }
    private val map = Board(lines[0].length, lines.size, lines.flatMap { it.toList() })
    private val start = map.points.first { map[it] == '.' }
    private val end = map.points.last { map[it] == '.' }
}
