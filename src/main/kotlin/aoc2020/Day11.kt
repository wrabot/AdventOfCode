package aoc2020

import tools.board.Board
import Day
import tools.board.Point

class Day11(test: Int? = null) : Day(2020, 11, test) {
    override fun solvePart1() = count(4) { seat, direction -> getOrNull(seat + direction)?.current == '#' }

    override fun solvePart2() = count(5) { seat, direction ->
        var current = seat
        while (true) {
            current += direction
            when (getOrNull(current)?.current) {
                '.' -> continue
                '#' -> return@count true
                else -> break
            }
        }
        false
    }

    data class Seat(var current: Char, var next: Char = current)

    private val directions = listOf(
        Point(1, 0), Point(1, -1), Point(0, -1), Point(-1, -1),
        Point(-1, 0), Point(-1, 1), Point(0, 1), Point(1, 1)
    )

    fun count(min: Int, occupied: Board<Seat>.(Point, Point) -> Boolean): Int {
        val plane = Board(lines[0].length, lines.size, lines.flatMap { it.map(::Seat) })
        while (true) {
            var modified = false
            plane.points.forEach { point ->
                val seat = plane[point]
                if (seat.current == 'L' && directions.none { plane.occupied(point, it) }) {
                    seat.next = '#'
                    modified = true
                }
                if (seat.current == '#' && directions.count { plane.occupied(point, it) } >= min) {
                    seat.next = 'L'
                    modified = true
                }
            }
            if (!modified) return plane.cells.count { it.current == '#' }
            plane.cells.forEach { it.current = it.next }
        }
    }
}
