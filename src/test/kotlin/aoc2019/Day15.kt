package aoc2019

import Day
import tools.XY
import tools.board.Direction4
import tools.board.Direction4.*

class Day15(test: Int? = null) : Day(test) {
    override fun solvePart1() = explorePart1()!!

    private fun explorePart1(
        xy: XY = XY(0, 0),
        done: MutableMap<XY, Long> = mutableMapOf(xy to 0),
        depth: Int = 1,
    ): Int? {
        for (direction in Direction4.entries) {
            val next = xy + direction.xy
            if (next in done) continue
            val status = runtime.execute(direction.forward())!!
            done[next] = status
            when (status) {
                0L -> continue
                2L -> return depth
            }
            explorePart1(next, done, depth + 1)?.also { return it }
            runtime.execute(direction.backward())
        }
        return null
    }

    override fun solvePart2() = explorePart2()

    private fun explorePart2(
        xy: XY = XY(0, 0),
        done: MutableMap<XY, Long> = mutableMapOf(xy to 0),
        depth: Int = 0,
    ): Int = Direction4.entries.maxOfOrNull { direction ->
        val next = xy + direction.xy
        if (next in done) return@maxOfOrNull depth
        val status = runtime.execute(direction.forward())!!
        done[next] = status
        if (status == 0L) return@maxOfOrNull depth
        explorePart2(next, done, depth + 1).also {
            runtime.execute(direction.backward())
        }
    } ?: depth

    private fun Direction4.forward() = when (this) {
        North -> 1L
        South -> 2L
        West -> 3L
        East -> 4L
    }

    private fun Direction4.backward() = when (this) {
        North -> 2L
        South -> 1L
        West -> 4L
        East -> 3L
    }

    private val code = lines.first().split(",").map { it.toLong() }
    private val runtime = Day9.Runtime(code)
}


