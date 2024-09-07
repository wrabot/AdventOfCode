package aoc2018

import Day
import tools.XY
import tools.board.Direction4
import tools.graph.shortPath
import tools.toWords
import tools.toXY

class Day22(test: Int? = null) : Day(test) {
    override fun solvePart1() = (0..target.x).sumOf { x -> (0..target.y).sumOf { y -> getType(x, y) } }

    override fun solvePart2() = shortPath(
        Node(XY(0, 0), Tool.Torch),
        end = Node(target, Tool.Torch),
        cost = this::cost
    ) { node ->
        val type = getType(node.xy)
        Direction4.entries.mapNotNull {
            val next = node.xy + it.xy
            if (next.x < 0 || next.y < 0) return@mapNotNull null
            if (getType(next) !in node.tool.types) return@mapNotNull null
            Node(next, node.tool)
        } + Node(node.xy, Tool.entries.first { it != node.tool && type in it.types })
    }.zipWithNext(this::cost).sum().toInt()

    private fun getType(xy: XY) = getType(xy.x, xy.y)
    private fun cost(o: Node, d: Node) = if (o.tool == d.tool) 1.0 else 7.0

    private enum class Tool(val types: Set<Int>) { Torch(setOf(0, 2)), ClimbingGear(setOf(0, 1)), None(setOf(1, 2)) }
    private data class Node(val xy: XY, val tool: Tool)

    // common

    private fun getType(x: Int, y: Int): Int = getErosionLevel(x, y) % 3
    private fun getErosionLevel(x: Int, y: Int): Int = erosionLevels.getOrPut(x) { mutableMapOf() }.getOrPut(y) {
        val geologicIndex = when {
            y == 0 -> x * 16807
            x == 0 -> y * 48271
            else -> getErosionLevel(x, y - 1) * getErosionLevel(x - 1, y)
        }
        (geologicIndex + depth) % 20183
    }

    private val depth = lines.first().toWords().last().toInt()
    private val target = lines.last().toWords().last().toXY(",")
    private val erosionLevels = mutableMapOf(0 to mutableMapOf(0 to 0), target.x to mutableMapOf(target.y to 0))
}
