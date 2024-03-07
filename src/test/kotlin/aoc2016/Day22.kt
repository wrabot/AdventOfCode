package aoc2016

import Day
import tools.board.Board
import tools.board.Board.XY
import tools.log
import tools.match

class Day22(test: Int? = null) : Day(test) {
    override fun solvePart1() = nodes.map { b ->
        nodes.count { a -> a.key != b.key && a.value.used != 0 && a.value.used <= b.value.size - b.value.used }
    }.sum()

    // except those that "used" is bigger than the empty size, all other "used" is less than capacity
    // so "heavy" can't move, any other can move to any through the empty one => Fifteen Puzzle Game
    override fun solvePart2(): Int {
        var count = 0
        val width = nodes.keys.maxOf { it.x } + 1
        val height = nodes.keys.maxOf { it.y } + 1

        // to analyse
        Board(
            width,
            height,
            List(height) { y ->
                List(width) { x ->
                    val used = nodes[XY(x, y)]!!.used
                    when {
                        used == 0 -> '_'
                        used <= emptySize -> '.'
                        else -> '#'
                    }
                }
            }.flatten()
        ).log()
        // the "heavy" are on one line with "door" on left side 

        // compute empty to width-2, 0
        val emptyTarget = XY(width - 2, 0)
        count += (empty - emptyTarget).manhattan()
        val minX = empty.x.coerceAtMost(emptyTarget.x)
        val minWallX = nodes.filter { it.value.used > emptySize }.minOf { it.key.x }
        count += (minX - minWallX + 1).coerceAtLeast(0) * 2

        // compute data to 0, 0 : move data width-1 times and move empty from back to front width-2 times
        count += (width - 1) + (width - 2) * 4

        return count
    }

    data class Node(val size: Int, val used: Int)

    private val nodes = lines.drop(2).associate { line ->
        line.match("/dev/grid/node-x(\\d+)-y(\\d+)\\s*(\\d+)T\\s*(\\d+)T.*".toRegex())!!.let {
            XY(it[0].toInt(), it[1].toInt()) to Node(it[2].toInt(), it[3].toInt())
        }
    }

    private val empty = nodes.firstNotNullOfOrNull { if (it.value.used == 0) it.key else null }!!
    private val emptySize = nodes[empty]!!.size
}
