package aoc2018

import Day
import tools.board.Board
import tools.board.XY
import tools.board.Direction4
import tools.match

class Day17(test: Int? = null) : Day(test) {
    override fun solvePart1() = underground.cells.count { it.c in listOf('|', '~') }
    override fun solvePart2() = underground.cells.count { it.c == '~' }

    private data class Cell(var c: Char) {
        override fun toString() = c.toString()
    }

    private val underground: Board<Cell>

    init {
        val xMap = mutableMapOf<Int, MutableList<IntRange>>()
        val yMap = mutableMapOf<Int, MutableList<IntRange>>()
        val regex = ".=(.*), .=(.*)\\.\\.(.*)".toRegex()
        lines.forEach { line ->
            val (k, s, e) = line.match(regex)!!
            val map = if (line[0] == 'x') xMap else yMap
            map.getOrPut(k.toInt()) { mutableListOf() }.add(s.toInt()..e.toInt())
        }
        val minX = xMap.keys.min().coerceAtMost(yMap.values.minOf { it.minOf { it.first } }) - 1
        val minY = yMap.keys.min().coerceAtMost(xMap.values.minOf { it.minOf { it.first } })
        val maxX = xMap.keys.max().coerceAtLeast(yMap.values.maxOf { it.maxOf { it.last } }) + 1
        val maxY = yMap.keys.max().coerceAtLeast(xMap.values.maxOf { it.maxOf { it.last } })
        val width = maxX - minX + 1
        val height = maxY - minY + 1
        underground = Board(width, height) { x, y ->
            val xa = x + minX
            val ya = y + minY
            Cell(if (xMap[xa]?.any { ya in it } == true || yMap[ya]?.any { xa in it } == true) '#' else '.')
        }
        fill(XY(500 - minX, 0))
    }

    private fun fill(start: XY) {
        val todo = mutableListOf(start)
        while (true) {
            val current = todo.removeLastOrNull() ?: break
            underground[current].c = '|'
            val down = current + Direction4.South.xy
            when (underground.getOrNull(down)?.c) {
                null, '|' -> Unit
                '.' -> todo.add(down)
                else -> {
                    val (leftRow, leftExit) = current.findSide(Direction4.West)
                    val (rightRow, rightExit) = current.findSide(Direction4.East)
                    if (leftExit == null && rightExit == null) {
                        (leftRow + rightRow + current).forEach { underground[it].c = '~' }
                        todo.add(current + Direction4.North.xy)
                    } else {
                        (leftRow + rightRow).forEach { underground[it].c = '|' }
                        todo.addAll(listOfNotNull(leftExit, rightExit))
                    }
                }
            }
        }
    }

    private fun XY.findSide(direction: Direction4): Pair<MutableList<XY>, XY?> {
        val row = mutableListOf<XY>()
        var current = this
        while (true) {
            current += direction.xy
            if (underground[current].c == '#') return row to null
            val c = underground[current + Direction4.South.xy].c
            if (c == '.' || c == '|') return row to current
            row.add(current)
        }
    }
}
