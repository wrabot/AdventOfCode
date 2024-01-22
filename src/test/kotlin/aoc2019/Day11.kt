package aoc2019

import Day
import tools.board.Board
import tools.board.Board.XY
import tools.board.Direction4.North
import tools.size

class Day11(test: Int? = null) : Day(test) {
    override fun solvePart1() = mutableMapOf<XY, Long>().apply { solve(this) }.size

    override fun solvePart2() = mutableMapOf(XY(0, 0) to 1L).apply { solve(this) }.run {
        val xRange = keys.minOf { it.x }..keys.maxOf { it.x }
        val yRange = keys.minOf { it.y }..keys.maxOf { it.y }
        val registration = Board(
            xRange.size(),
            yRange.size(),
            yRange.flatMap { y ->
                xRange.map { x ->
                    if (getOrDefault(XY(x - xRange.first, y - yRange.first), 0) == 0L) ' ' else 'X'
                }
            }
        )
        "\n" + registration.toString().trimIndent()
    }

    fun solve(panels: MutableMap<XY, Long>) {
        var direction = North
        var position = XY(0, 0)
        val runtime = Day9.Runtime(code)
        while (true) {
            val color = runtime.execute(panels.getOrDefault(position, 0)) ?: break
            panels[position] = color
            direction = if (runtime.execute(null) == 0L) direction.left else direction.right
            position += direction.xy
        }
    }

    private val code = lines.first().split(",").map { it.toLong() }
}
