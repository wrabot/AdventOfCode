package aoc2022

import Day
import tools.Point
import tools.range.merge
import tools.range.size
import kotlin.math.abs

class Day15(test: Int? = null) : Day(test) {
    override fun solvePart1() =
        couples.mapNotNull { it.scan(rowPart1, Long.MIN_VALUE..Long.MAX_VALUE) }.merge()
            .sumOf { it.size } - beacons.count { it.y == rowPart1 }

    override fun solvePart2() = (0..size).firstNotNullOf { y ->
        val ranges = couples.mapNotNull { it.scan(y.toDouble(), 0L..size) }.merge()
        if (ranges.sumOf { it.size } == size.toLong()) (ranges.first().last + 1) * 4000000 + y else null
    }

    private data class Couple(
        val sensor: Point,
        val beacon: Point,
        val distance: Double = (sensor - beacon).manhattan()
    ) {
        fun scan(y: Double, range: LongRange) = (distance - abs(y - sensor.y)).takeIf { it >= 0 }?.let {
            (sensor.x - it).toLong().coerceAtLeast(range.first)..(sensor.x + it).toLong().coerceAtMost(range.last)
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
