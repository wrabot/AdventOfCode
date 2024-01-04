package aoc2015

import Day
import tools.board.Point

class Day3 : Day(2015, 3) {
    override fun solvePart1(): Any {
        val houses = mutableSetOf(start)
        var current = start
        lines[0].forEach {
            current = current.next(it)
            houses.add(current)
        }
        return houses.count()
    }

    override fun solvePart2(): Any {
        val houses = mutableSetOf(start)
        var previous = start
        var current = start
        lines[0].forEach {
            val next = previous.next(it)
            previous = current
            current = next
            houses.add(current)
        }
        return houses.count()
    }

    private val start = Point(0, 0)

    fun Point.next(direction: Char) = when (direction) {
        '<' -> Point(x - 1, y)
        '>' -> Point(x + 1, y)
        '^' -> Point(x, y - 1)
        'v' -> Point(x, y + 1)
        else -> error("invalid direction")
    }
}
