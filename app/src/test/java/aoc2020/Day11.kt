package aoc2020

import forEachInput
import tools.log

object Day11 {
    fun solve() = forEachInput(2020, 11, 2) { lines ->
        val width = lines[0].length
        val height = lines.size
        val initialMap = lines.flatMap { it.toList() }

        log("part 1: ")
        Seats(initialMap, width, height, 4) { seat, direction ->
            get(seat.first + direction.first, seat.second + direction.second) == '#'
        }.update()

        log("part 2: ")
        Seats(initialMap, width, height, 5) { seat, direction ->
            var x = seat.first
            var y = seat.second
            var c: Char?
            do {
                x += direction.first
                y += direction.second
                c = get(x, y)
            } while (c == '.')
            c == '#'
        }.update()
    }

    data class Seats(private var map: List<Char>, val width: Int, val height: Int, val min: Int, val occupied: Seats.(Pair<Int, Int>, Pair<Int, Int>) -> Boolean) {
        fun update() {
            do {
                var modified = false
                map = map.mapIndexed { index, c ->
                    val seat = Pair(index % width, index / width)
                    when (c) {
                        'L' -> if (adjacent(seat) == 0) '#' else 'L'
                        '#' -> if (adjacent(seat) >= min) 'L' else '#'
                        else -> c
                    }.apply { if (c != this) modified = true }
                }
            } while (modified)
            map.count { it == '#' }.log()
        }

        fun get(x: Int, y: Int) = if (x in 0 until width && y in 0 until height) map[y * width + x] else null

        private val directions = listOf(1 to 0, 1 to -1, 0 to -1, -1 to -1, -1 to 0, -1 to 1, 0 to 1, 1 to 1)
        private fun adjacent(seat: Pair<Int, Int>) = directions.count { occupied(seat, it) }
    }
}
