package aoc2022

import tools.Day
import tools.shortPath

class Day16(test: Int? = null) : Day(2022, 16, test, true) {
    override fun solvePart1() = max(targets, "AA", 0, 0, 30)
    override fun solvePart2() = (0 until (1 shl (targets.size - 1))).maxOf { id ->
        val mine = targets.filterIndexed { index, _ -> id and (1 shl index) == 0 }
        val elephant = targets - mine
        max(mine, "AA", 0, 0, 26) + max(elephant, "AA", 0, 0, 26)
    }

    private fun max(remaining: List<String>, current: String, release: Int, total: Int, time: Int): Int = remaining.mapNotNull {
        val delay = distances[current]!![it]!! + 1
        if (delay > time) null else max(remaining.minus(it), it, release + valves[it]!!.rate, total + release * delay, time - delay)
    }.maxOrNull() ?: (total + release * time)

    data class Valve(val name: String, val rate: Int, val neighbors: List<String>)

    private val valves = lines.associate { line ->
        line.split("Valve ", " has flow rate=", "; tunnels lead to valves ", "; tunnel leads to valve ", ", ").let {
            it[1] to Valve(it[1], it[2].toInt(), it.drop(3))
        }
    }

    private val targets = valves.values.filter { it.rate > 0 }.map { it.name }

    private val distances = (targets + "AA").associateWith { start ->
        targets.minus(start).associateWith { end ->
            shortPath(start, end) { valves[it]!!.neighbors.associateWith { 1 } }.size - 1
        }
    }
}