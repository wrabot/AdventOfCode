package aoc2024

import Day
import tools.XY
import tools.board.Direction4
import tools.board.toBoard
import tools.graph.distancesFromOneToAll
import tools.graph.shortPath

class Day16(test: Int? = null) : Day(test) {
    private data class Node(val p: XY, val d: Direction4) {
        fun neighbors(): List<Node> {
            val left = d.turn(1)
            val right = d.turn(-1)
            return listOf(Node(p + d.xy, d), Node(p + left.xy, left), Node(p + right.xy, right))
        }
    }

    private val map = lines.toBoard { it }
    private val start = Node(map.xy.first { map[it] == 'S' }, Direction4.East)

    private fun List<Node>.removesWalls() = filter { map[it.p] != '#' }

    private fun cost(a: Node, b: Node) = if (a.d != b.d) 1001.0 else 1.0

    private val minCost = shortPath(start = start, isEnd = { map[it.p] == 'E' }, cost = ::cost) {
        it.neighbors().removesWalls()
    }.zipWithNext(::cost).sum().toInt()

    override fun solvePart1() = minCost

    override fun solvePart2() = findPlaces(emptyList(), start, 0).size

    private val vertices = map.xy.filter { map[it] != '#' }.flatMap { p -> Direction4.entries.map { d -> Node(p, d) } }
    private val edges = vertices.mapIndexed { index: Int, node: Node ->
        index to node.neighbors().removesWalls().associate { vertices.indexOf(it) to cost(node, it) }
    }.toMap()
    private val distancesFromStart = distancesFromOneToAll(vertices.size, vertices.indexOf(start)) { edges[it]!! }

    private fun findPlaces(path: List<XY>, node: Node, cost: Int): Set<XY> {
        val distance = distancesFromStart[vertices.indexOf(node)].toInt()
        if (cost > distance) return emptySet()
        if (map[node.p] == 'E' && cost == minCost) return (path + node.p).toSet()
        return Direction4.entries.flatMap { d ->
            val next = Node(node.p + d.xy, d)
            if (map[next.p] == '#' || next.p in path) return@flatMap emptySet()
            findPlaces(path + node.p, next, cost + if (d == node.d) 1 else 1001)
        }.toSet()
    }
}
