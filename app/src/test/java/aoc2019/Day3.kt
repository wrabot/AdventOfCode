package aoc2019

import forEachInput
import tools.log
import kotlin.math.abs

object Day3 {
    fun solve() = forEachInput(2019, 3, 4) { lines ->
        val wires = lines.map { wire ->
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
        val intersections = wires.reduce { acc, list -> (acc intersect list).toList() }.minus(0 to 0).minus(0 to 0)

        log("part 1: ")
        intersections.map { abs(it.first) + abs(it.second) }.minOrNull().log()

        log("part 2: ")
        intersections.map { point -> wires.map { it.indexOf(point) }.sum() }.minOrNull().log()
    }
}