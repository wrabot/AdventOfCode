package aoc2022

import tools.Day
import tools.Point
import kotlin.math.abs
import kotlin.math.max

class Day15(test: Int? = null) : Day(2022, 15, test, true) {
    override fun solvePart1() =
        couples.mapNotNull { it.scanPart1(rowPart1) }.merge().sumOf { it.size() } - beacons.count { it.y == rowPart1 }

    override fun solvePart2() = (0..size).firstNotNullOf { y ->
        val ranges = couples.mapNotNull { it.scanPart2(y, size) }.merge()
        if (ranges.sumOf { it.size() } == size) (ranges.first().last + 1) * 4000000L + y else null
    }

    private fun List<IntRange>.merge() = sortedBy { it.first }.run {
        drop(1).fold(listOf(first())) { acc, range ->
            val last = acc.last()
            if (range.first in acc.last())
                acc.dropLast(1).plusElement(last.first..max(last.last, range.last))
            else
                acc.plusElement(range)
        }
    }

    private fun IntRange.size() = last - first + 1

    data class Couple(val sensor: Point, val beacon: Point, val distance: Int = sensor.distance(beacon)) {
        fun scanPart1(y: Int)= (distance - abs(y - sensor.y)).takeIf { it >= 0 }?.let {
            (sensor.x - it)..(sensor.x + it)
        }

        fun scanPart2(y: Int, max: Int) = (distance - abs(y - sensor.y)).takeIf { it >= 0 }?.let {
            (sensor.x - it).coerceAtLeast(0)..(sensor.x + it).coerceAtMost(max)
        }
    }

    private val rowPart1 = lines[0].toInt()
    private val size = lines[1].toInt()
    private val couples = lines.drop(2).map { line ->
        line.split('=', ',', ':').let {
            Couple(Point(it[1].toInt(), it[3].toInt()), Point(it[5].toInt(), it[7].toInt()))
        }
    }
    private val beacons = couples.map { it.beacon }.distinct()
}
