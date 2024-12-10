package aoc2024

import Day
import tools.XY
import tools.board.toBoard

class Day10(test: Int? = null) : Day(test) {
    private val map = lines.toBoard { it }
    private val starts = map.xy.filter { map[it] == '0' }

    override fun solvePart1() = starts.sumOf { solve(it, mutableSetOf()) }
    override fun solvePart2() = starts.sumOf { solve(it, mutableListOf()) }

    private fun solve(start: XY, tops: MutableCollection<XY>): Int {
        val todo = mutableListOf(start)
        while (true) {
            val current = todo.removeLastOrNull() ?: break
            val level = map[current]
            if (level == '9') {
                tops.add(current)
            } else {
                todo.addAll(map.neighbors4(current).filter { map[it] == level + 1 })
            }
        }
        return tops.size
    }
}
