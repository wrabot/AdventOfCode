package aoc2024

import Day
import tools.graph.findAllMaximalCliques
import tools.graph.intGraph
import tools.sequence.select

class Day23(test: Int? = null) : Day(test) {
    private val graph = lines.flatMap { line ->
        line.split("-").let { listOf(it[0] to it[1], it[1] to it[0]) }
    }.groupBy({ it.first }, { it.second })

    private val names = graph.keys.toTypedArray()
    private val cliques = findAllMaximalCliques(intGraph(names, graph), true).map { it.map(names::get) }

    override fun solvePart1() = cliques.filter { it.size >= 3 }.flatMap { it.select(3) }.distinct()
        .count { group -> group.any { it[0] == 't' } }

    override fun solvePart2() = cliques.maxBy { it.size }.sorted().joinToString(",")
}
