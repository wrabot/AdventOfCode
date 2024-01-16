package aoc2022

import Day
import tools.geometry.Origin
import tools.geometry.Point

class Day9(test: Int? = null) : Day(test) {
    override fun solvePart1() = mutableSetOf(Origin).apply {
        var head = Origin
        var tail = Origin
        moves.forEach { move ->
            repeat(move.second) {
                head += move.first
                tail = tail.moveTail(head)
                add(tail)
            }
        }
    }.size

    override fun solvePart2() = mutableSetOf(Origin).apply {
        val rope = Array(10) { Origin }
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
        move(rope, index + 1, rope[index + 1].moveTail(destination))
    }

    private fun Point.moveTail(head: Point): Point {
        val diff = (head - this).run { Point(x.middle(), y.middle()) }
        return if (diff == Origin) this else head - diff
    }

    private fun Double.middle() = (this / 2).toInt()

    private val directions = mapOf(
        "U" to Point(0, 1),
        "D" to Point(0, -1),
        "R" to Point(1, 0),
        "L" to Point(-1, 0),
    )

    private val moves = lines.map {
        val (direction, distance) = it.split(" ")
        directions[direction]!! to distance.toInt()
    }
}
