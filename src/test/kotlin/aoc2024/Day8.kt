package aoc2024

import Day
import tools.XY
import tools.board.toBoard
import tools.sequence.select

class Day8(test: Int? = null) : Day(test) {
    private val map = lines.toBoard { it }
    private val antennaByFrequency = map.xy.groupBy { map[it] }.minus('.')

    override fun solvePart1() = mutableSetOf<XY>().apply {
        antennaByFrequency.entries.forEach { entry ->
            entry.value.select(2).forEach {
                val d = it[1] - it[0]
                add(it[0] - d)
                add(it[1] + d)
            }
        }
    }.filter { map.getOrNull(it) != null }.size

    override fun solvePart2() = mutableSetOf<XY>().apply {
        antennaByFrequency.entries.forEach { entry ->
            entry.value.select(2).forEach {
                val d = it[1] - it[0]
                var forward = it[1]
                while (map.getOrNull(forward) != null) {
                    add(forward)
                    forward += d
                }
                var backward = it[0]
                while (map.getOrNull(backward) != null) {
                    add(backward)
                    backward -= d
                }
            }
        }
    }.size
}
