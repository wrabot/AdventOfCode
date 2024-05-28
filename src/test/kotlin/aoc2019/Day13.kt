package aoc2019

import Day
import tools.board.XY
import kotlin.math.sign

class Day13(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        val blocks = mutableListOf<XY>()
        val runtime = Day9.Runtime(code)
        while (true) {
            val x = runtime.execute(null) ?: break
            val y = runtime.execute(null) ?: break
            val c = runtime.execute(null) ?: break
            if (c == 2L) blocks.add(XY(x.toInt(), y.toInt()))
        }
        return blocks.size
    }

    override fun solvePart2(): Long {
        var score = 0L
        var paddle = 0
        var ball = 0
        var joystick = 0
        val runtime = Day9.Runtime(listOf(2L) + code.drop(1))
        while (true) {
            val x = runtime.execute(joystick.toLong())?.toInt() ?: break
            runtime.execute(null) // ignore y
            val c = runtime.execute(null) ?: break
            if (x == -1) score = c
            when (c) {
                3L -> paddle = x
                4L -> ball = x
            }
            joystick = (ball - paddle).sign
        }
        return score
    }

    private val code = lines.first().split(",").map { it.toLong() }
}
