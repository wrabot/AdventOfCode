package aoc2024

import Day
import tools.XY
import tools.board.toBoard
import tools.debug
import tools.graph.shortPath

class Day20(test: Int? = null) : Day(test) {
    private val map = lines.toBoard { it }
    private val start = map.xy.first { map[it] == 'S' }
    private val end = map.xy.first { map[it] == 'E' }
    private val path = shortPath(start, end) { map.neighbors4(it).filter { n -> map[n] != '#' } }

    override fun solvePart1() = cheatCounts(2)
    override fun solvePart2() = cheatCounts(20)

    private fun cheatCounts(cheatDuration: Int) = path.flatMapIndexed { startTime: Int, cheatStart: XY ->
        path.drop(startTime).mapIndexedNotNull { endTime, cheatEnd ->
            (cheatEnd - cheatStart).manhattan().takeIf { it <= cheatDuration }?.let { endTime - it }
        }
    }.apply { groupingBy { it }.eachCount().toSortedMap().debug() }.count { it >= 100 }
}
