package aoc2024

import Day
import tools.XY
import tools.board.Board
import tools.board.CharCell
import tools.debug
import tools.toXY

class Day14(test: Int? = null) : Day(test) {
    private val wh = lines[0].toXY(" ")
    private val pv = lines.drop(1).map { line -> line.split(" ").map { it.drop(2).toXY(",") } }

    override fun solvePart1(): Int {
        var bots = pv.map { it[0] }
        repeat(100) {
            bots = bots.mapIndexed { index, p -> p.addMod(pv[index][1], wh) }
        }
        return bots.safetyFactor()
    }

    override fun solvePart2(): Int {
        var bots = pv.map { it[0] }
        var minBots = bots
        var minSafetyFactor = bots.safetyFactor()
        var minCount = 0
        var count = 1
        while (true) {
            bots = bots.mapIndexed { index, p -> p.addMod(pv[index][1], wh) }
            val safetyFactor = bots.safetyFactor()
            if (safetyFactor == minSafetyFactor) break
            if (safetyFactor < minSafetyFactor) {
                minSafetyFactor = safetyFactor
                minBots = bots
                minCount = count
            }
            count++
        }
        minBots.debug()
        return minCount
    }

    private fun List<XY>.safetyFactor(): Int {
        val top = filter { it.y < wh.y / 2 }
        val bottom = filter { it.y > wh.y / 2 }
        val topLeft = top.count { it.x < wh.x / 2 }
        val topRight = top.count { it.x > wh.x / 2 }
        val bottomLeft = bottom.count { it.x < wh.x / 2 }
        val bottomRight = bottom.count { it.x > wh.x / 2 }
        return topLeft * topRight * bottomLeft * bottomRight
    }

    private fun XY.addMod(v: XY, s: XY) = XY((x + v.x).mod(s.x), (y + v.y).mod(s.y))

    private fun List<XY>.debug() {
        debug("---------------------")
        val board = Board(wh.x, wh.y, List(wh.x * wh.y) { CharCell('.') })
        forEach { board[it].c = 'X' }
        board.debug()
    }
}

