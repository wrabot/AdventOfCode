package aoc2020

import Day
import tools.Point

class Day24(test: Int? = null) : Day(test) {
    override fun solvePart1(): Any {
        lines.forEach { Point.Zero.goToTile(it).run { if (!blacks.remove(this)) blacks.add(this) } }
        return blacks.size
    }

    override fun solvePart2(): Any {
        val directions = listOf("e", "w", "ne", "nw", "se", "sw")
        val neighbors = mutableMapOf<Point, List<Point>>()
        return (1..100).fold(blacks.toSet()) { tiles, _ ->
            tiles.flatMap { tile -> neighbors.getOrPut(tile) { directions.map { tile.goToTile(it) } } }
                .groupingBy { it }.eachCount().filter { it.value == 2 || (it.value == 1 && it.key in tiles) }.keys
        }.size
    }

    private fun Point.goToTile(path: String): Point {
        var x = x
        var y = y
        var previous: Char? = null
        path.forEach {
            when (it) {
                'n' -> y--
                's' -> y++
                'e' -> if (previous != 's') x++
                'w' -> if (previous != 'n') x--
            }
            previous = it
        }
        return Point(x, y)
    }

    private val blacks = mutableSetOf<Point>()
}
