package aoc2022

import tools.Board
import tools.Day
import tools.Point
import tools.log
import kotlin.math.abs

class Day9(test: Int? = null) : Day(2022, 9, test) {
    override fun solvePart1() = mutableSetOf(Point(0, 0)).apply {
        var head = Point(0, 0)
        var tail = Point(0, 0)
        moves.forEach { move ->
            repeat(move.second) {
                head += move.first
                val diff = head - tail
                if (abs(diff.x) == 2) {
                    tail = Point(head.x - diff.x / 2, head.y)
                } else if (abs(diff.y) == 2) {
                    tail = Point(head.x, head.y - diff.y / 2)
                }
                add(tail)
            }
        }
    }.size

    override fun solvePart2() = mutableSetOf(Point(0, 0)).apply {
        val rope = Array(10) { Point(0, 0) }
        moves.forEach { move ->
            repeat(move.second) {
                move(rope, 0, rope[0] + move.first)
            }
        }
    }.size

   private fun MutableSet<Point>.move(rope: Array<Point>, index: Int, destination: Point) {
        rope[index] = destination
        if (index == rope.size - 1) {
            add(rope[index])
            return
        }
        val delta = rope[index + 1] - destination
        val dx = if (abs(delta.x) == 2) delta.x / 2 else 0
        val dy = if (abs(delta.y) == 2) delta.y / 2 else 0
        if (dx != 0 || dy != 0) move(rope, index + 1, Point(destination.x + dx, destination.y + dy))
    }

    private val moves = lines.map {
        val (direction, distance) = it.split(" ")
        when (direction) {
            "U" -> Point(0, 1)
            "D" -> Point(0, -1)
            "R" -> Point(1, 0)
            "L" -> Point(-1, 0)
            else -> error("!!!")
        } to distance.toInt()
    }

    // to debug
    private fun Array<Point>.log() {
        val minX = minOf { it.x }
        val minY = minOf { it.y }
        val width = maxOf { it.x } - minX + 1
        val height = maxOf { it.y } - minY + 1
        Board(width, height, MutableList(width * height) { '.' }.apply {
            this@log.forEachIndexed { index, point ->
                this[(height - 1 - point.y + minY) * width + (point.x - minX)] = '0' + index
            }
        }).log()
    }
}
