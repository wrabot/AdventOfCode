package aoc2015

import Day
import tools.geometry.Point

class Day3 : Day() {
    override fun solvePart1(): Any {
        val houses = mutableSetOf(Point.Zero)
        var current = Point.Zero
        lines[0].forEach {
            current = current.next(it)
            houses.add(current)
        }
        return houses.count()
    }

    override fun solvePart2(): Any {
        val houses = mutableSetOf(Point.Zero)
        var previous = Point.Zero
        var current = Point.Zero
        lines[0].forEach {
            val next = previous.next(it)
            previous = current
            current = next
            houses.add(current)
        }
        return houses.count()
    }

    fun Point.next(direction: Char) = when (direction) {
        '<' -> Point(x - 1, y)
        '>' -> Point(x + 1, y)
        '^' -> Point(x, y - 1)
        'v' -> Point(x, y + 1)
        else -> error("invalid direction")
    }
}
