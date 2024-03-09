package aoc2016

import Day
import tools.board.toBoard
import tools.board.toGraph
import tools.graph.shortPath

class Day24(test: Int? = null) : Day(test) {
    override fun solvePart1() = solve(graph.size)
    override fun solvePart2() = solve(graph.size + 1)

    private val graph = lines.toBoard { it }.toGraph(
        isStart = Char::isDigit,
        isEnd = Char::isDigit,
        isWall = { this == '#' }
    ) { it.size }
    
    private fun solve(size: Int) = shortPath(
        start = "0",
        isEnd = { it.length == size },
        cost = { a, b -> graph[a.last()]!![b.last()]!!.toDouble() },
        neighbors = { path ->
            if (path.length < graph.size)
                graph[path.last()]!!.filter { it.key !in path }.map { path + it.key }
            else
                listOf(path + '0')
        }
    ).last().zipWithNext().sumOf { graph[it.first]!![it.second]!! }
}
