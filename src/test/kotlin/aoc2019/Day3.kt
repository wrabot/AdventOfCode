package aoc2019

import Day
import tools.geometry.Origin
import tools.geometry.Point
import kotlin.math.absoluteValue

class Day3(test: Int? = null) : Day(test) {
    override fun solvePart1() = intersections.minOf { it.x.absoluteValue + it.y.absoluteValue }.toInt()

    override fun solvePart2() = intersections.minOf { point -> wires.sumOf { it.indexOf(point) } }

    private val wires = lines.map { line ->
        line.split(",").map { it.first() to it.drop(1).toInt() }.fold(listOf(Origin)) { acc, move ->
            acc + acc.last().let { point ->
                when (move.first) {
                    'R' -> (1..move.second).map { Point(point.x + it, point.y) }
                    'L' -> (1..move.second).map { Point(point.x - it, point.y) }
                    'U' -> (1..move.second).map { Point(point.x, point.y - it) }
                    'D' -> (1..move.second).map { Point(point.x, point.y + it) }
                    else -> error("invalid direction")
                }
            }
        }
    }

    private val intersections = (wires[0].minus(Origin) intersect wires[1].toSet()).toList()
}
