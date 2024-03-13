package aoc2017

import Day
import tools.graph.dfs

class Day12(test: Int? = null) : Day(test) {
    override fun solvePart1() = group(0).size
    override fun solvePart2() = links.indices.map(::group).distinct().size

    private val links = lines.map { line -> line.split(" <-> ", ", ").drop(1).map { it.toInt() } }
    private fun group(start: Int) = mutableSetOf<Int>().apply {
        dfs(links.size, start) {
            add(it)
            links[it]
        }
    }
}
