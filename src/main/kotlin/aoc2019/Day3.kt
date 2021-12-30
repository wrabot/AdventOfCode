package aoc2019

import tools.Day
import kotlin.math.absoluteValue

class Day3(test: Int? = null) : Day(2019, 3, test) {
    override fun solvePart1() = intersections.minOf { it.first.absoluteValue + it.second.absoluteValue }

    override fun solvePart2() = intersections.minOf { point -> wires.sumOf { it.indexOf(point) } }

    private val wires = lines.map { wire ->
        wire.split(",").map { it.first() to it.drop(1).toInt() }.fold(listOf(0 to 0)) { acc, v ->
            acc + acc.last().let { pos ->
                when (v.first) {
                    'R' -> (1..v.second).map { (pos.first + it) to pos.second }
                    'L' -> (1..v.second).map { (pos.first - it) to pos.second }
                    'U' -> (1..v.second).map { pos.first to (pos.second - it) }
                    'D' -> (1..v.second).map { pos.first to (pos.second + it) }
                    else -> error("invalid direction")
                }
            }
        }
    }

    private val intersections = wires.reduce { acc, list -> (acc intersect list).toList() }.minus(0 to 0).minus(0 to 0)
}
