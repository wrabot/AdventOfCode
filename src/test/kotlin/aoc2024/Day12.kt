package aoc2024

import Day
import tools.XY
import tools.board.Direction4
import tools.board.toBoard
import tools.graph.zone

class Day12(test: Int? = null) : Day(test) {
    private val map = lines.toBoard { it }
    private val zones = mutableListOf<Pair<Char, List<XY>>>()

    init {
        val done = mutableSetOf<XY>()
        for (start in map.xy) {
            if (start in done) continue
            val type = map[start]
            val zone = zone(start) { current -> map.neighbors4(current).filter { map[it] == type } }
            done.addAll(zone)
            zones.add(type to zone)
        }
    }

    override fun solvePart1() = zones.sumOf { (type, zone) ->
        val perimeter = zone.sumOf { c -> 4 - c.neighbors4().count { map.getOrNull(it) == type } }
        zone.size * perimeter
    }

    override fun solvePart2() = zones.sumOf { (type, zone) ->
        val fences = Direction4.entries.flatMap { direction ->
            val fenceParts = zone.filter { map.getOrNull(it + direction.xy) != type }
            if (direction.xy.x != 0)
                fenceParts.groupBy({ it.x }, { it.y }).values
            else
                fenceParts.groupBy({ it.y }, { it.x }).values
        }.sumOf { line -> 1 + line.sorted().zipWithNext { a, b -> a + 1 != b }.count { it } }
        zone.size * fences
    }
}
