package aoc2020

import Day
import tools.board.Board
import tools.board.Direction8
import tools.XY
import tools.board.toBoard

class Day11(test: Int? = null) : Day(test) {
    override fun solvePart1() = count(4) { seat, direction -> getOrNull(seat + direction.xy)?.current == '#' }

    override fun solvePart2() = count(5) { seat, direction ->
        var current = seat
        while (true) {
            current += direction.xy
            when (getOrNull(current)?.current) {
                '.' -> continue
                '#' -> return@count true
                else -> break
            }
        }
        false
    }

    private data class Seat(var current: Char, var next: Char = current)

    private fun count(min: Int, occupied: Board<Seat>.(XY, Direction8) -> Boolean): Int {
        val plane = lines.toBoard(::Seat)
        while (true) {
            var modified = false
            plane.xy.forEach { xy ->
                val seat = plane[xy]
                if (seat.current == 'L' && Direction8.entries.none { plane.occupied(xy, it) }) {
                    seat.next = '#'
                    modified = true
                }
                if (seat.current == '#' && Direction8.entries.count { plane.occupied(xy, it) } >= min) {
                    seat.next = 'L'
                    modified = true
                }
            }
            if (!modified) return plane.cells.count { it.current == '#' }
            plane.cells.forEach { it.current = it.next }
        }
    }
}
