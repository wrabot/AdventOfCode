package aoc2018

import Day
import tools.board.Board
import tools.board.toBoard

class Day18(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        var area = lines.toBoard { it }
        repeat(10) { area = area.round() }
        return area.resources()
    }

    override fun solvePart2(): Int {
        val target = 1_000_000_000
        var area = lines.toBoard { it }
        val resources = mutableListOf<Int>()
        val cache = mutableMapOf<String, Int>()
        while (true) {
            val previous = cache.putIfAbsent(area.toString(), resources.size)
            if (previous != null) return resources[(target - previous) % (resources.size - previous) + previous]
            resources.add(area.resources())
            area = area.round()
        }
    }

    private fun Board<Char>.round() = Board(width, height, xy.map { xy ->
        val trees = neighbors8(xy).count { get(it) == '|' }
        val lumbers = neighbors8(xy).count { get(it) == '#' }
        when (get(xy)) {
            '.' -> if (trees >= 3) '|' else '.'
            '|' -> if (lumbers >= 3) '#' else '|'
            else -> if (trees >= 1 && lumbers >= 1) '#' else '.'
        }
    })

    private fun Board<Char>.resources() = cells.count { it == '|' } * cells.count { it == '#' }
}
