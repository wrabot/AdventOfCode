package aoc2020

import tools.Day
import kotlin.math.absoluteValue

class Day12(test: Int? = null) : Day(2020, 12, test) {
    override fun solvePart1(): Any {
        var x = 0
        var y = 0
        var dir = 0
        lines.map { Pair(it.first(), it.drop(1).toInt()) }.forEach { (op, value) ->
            when (op) {
                'R' -> dir = (dir - value + 360) % 360
                'L' -> dir = (dir + value) % 360
                'E' -> x += value
                'N' -> y -= value
                'W' -> x -= value
                'S' -> y += value
                'F' -> when (dir) {
                    0 -> x += value
                    90 -> y -= value
                    180 -> x -= value
                    270 -> y += value
                    else -> error("Should not happen")
                }
                else -> error("Should not happen")
            }
        }
        return x.absoluteValue + y.absoluteValue
    }

    override fun solvePart2(): Any {
        var x = 0
        var y = 0
        var wx = 10
        var wy = -1
        lines.map { Pair(it.first(), it.drop(1).toInt()) }.forEach { (op, value) ->
            when (op) {
                'R' -> rotate(wx, wy, 360 - value).let {
                    wx = it.first
                    wy = it.second
                }
                'L' -> rotate(wx, wy, value).let {
                    wx = it.first
                    wy = it.second
                }
                'E' -> wx += value
                'N' -> wy -= value
                'W' -> wx -= value
                'S' -> wy += value
                'F' -> {
                    x += value * wx
                    y += value * wy
                }
                else -> error("Should not happen")
            }
        }
        return x.absoluteValue + y.absoluteValue
    }

    private fun rotate(x: Int, y: Int, degree: Int) = when (degree) {
        90 -> y to -x
        180 -> -x to -y
        270 -> -y to x
        else -> error("Should not happen")
    }
}
