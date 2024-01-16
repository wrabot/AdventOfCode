package aoc2022

import Day
import tools.geometry.Point
import tools.merge
import tools.size
import kotlin.math.abs

class Day15(test: Int? = null) : Day(test) {
    override fun solvePart1() =
        couples.mapNotNull { it.scan(rowPart1, Int.MIN_VALUE..Int.MAX_VALUE) }.merge()
            .sumOf { it.size() } - beacons.count { it.y == rowPart1 }

    override fun solvePart2() = (0..size).firstNotNullOf { y ->
        val ranges = couples.mapNotNull { it.scan(y.toDouble(), 0..size) }.merge()
        if (ranges.sumOf { it.size() } == size) (ranges.first().last + 1) * 4000000L + y else null
    }

    data class Couple(val sensor: Point, val beacon: Point, val distance: Double = (sensor - beacon).manhattan()) {
        fun scan(y: Double, range: IntRange) = (distance - abs(y - sensor.y)).takeIf { it >= 0 }?.let {
            (sensor.x - it).toInt().coerceAtLeast(range.first)..(sensor.x + it).toInt().coerceAtMost(range.last)
        }
    }

    private val rowPart1 = lines[0].toDouble()
    private val size = lines[1].toInt()
    private val couples = lines.drop(2).map { line ->
        line.split('=', ',', ':').let {
            Couple(Point(it[1].toDouble(), it[3].toDouble()), Point(it[5].toDouble(), it[7].toDouble()))
        }
    }
    private val beacons = couples.map { it.beacon }.distinct()
}
