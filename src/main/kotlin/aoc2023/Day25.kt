package aoc2023

import Day
import tools.graph.EdmondsKarp
import tools.graph.bfs

class Day25(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        val edmondsKarp = EdmondsKarp(nodes.size, wires.flatMap {
            listOf(
                EdmondsKarp.Edge(nodes.indexOf(it.first()), nodes.indexOf(it.last()), 1.0),
                EdmondsKarp.Edge(nodes.indexOf(it.last()), nodes.indexOf(it.first()), 1.0),
            )
        })
        val source = 0
        var sink = 1
        while (edmondsKarp.maxFlow(source, sink++) != 3.0) edmondsKarp.clear()
        val neighbors = edmondsKarp.edges.groupBy({ it.source }, { it.destination })
        var connected = 0
        bfs(nodes.size, source) { current ->
            connected++
            neighbors[current].orEmpty().mapNotNull {
                if (edmondsKarp.flows[current][it] == 0.0) it else null
            }
        }
        return connected * (nodes.size - connected)
    }

    override fun solvePart2() = Unit

    private val wires = lines.flatMap { line ->
        val split = line.split(": ", " ")
        split.drop(1).map { setOf(split[0], it) }
    }

    private val nodes = wires.flatten().distinct().sorted()
}
