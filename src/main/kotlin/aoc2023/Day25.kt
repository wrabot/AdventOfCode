package aoc2023

import tools.Day
import tools.EdmondsKarp
import tools.ValuedEdge

class Day25(test: Int? = null) : Day(2023, 25, test) {
    override fun solvePart1(): Int {
        val edmondsKarp = EdmondsKarp(nodes.size, wires.flatMap {
            listOf(
                ValuedEdge(nodes.indexOf(it.first()), nodes.indexOf(it.last()), 1),
                ValuedEdge(nodes.indexOf(it.last()), nodes.indexOf(it.first()), 1),
            )
        })
        var sink = 0
        while (edmondsKarp.maxFlow(0, sink++) != 3L) edmondsKarp.clear()
        return edmondsKarp.connected(0).size.let { it * (nodes.size - it) }
    }

    override fun solvePart2() = Unit

    private val wires = lines.flatMap { line ->
        val split = line.split(": ", " ")
        split.drop(1).map { setOf(split[0], it) }
    }

    private val nodes = wires.flatten().distinct().sorted()
}
