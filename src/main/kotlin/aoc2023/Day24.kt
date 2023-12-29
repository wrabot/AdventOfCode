package aoc2023

import tools.Day
import tools.PointD
import tools.gcd
import kotlin.math.abs
import kotlin.math.roundToLong

class Day24(test: Int? = null) : Day(2023, 24, test) {
    override fun solvePart1() = hailstones.mapIndexed { index, hailstone ->
        hailstones.drop(index + 1).count {
            val point = intersect2D(hailstone, it) ?: return@count false
            val t1 = hailstone.run { (point.x - first.x) / second.x }
            val t2 = it.run { (point.x - first.x) / second.x }
            (t1 >= 0 && t2 >= 0 && inLimit(point.x) && inLimit(point.y))
        }
    }.sum()

    private fun inLimit(d: Double) = d >= limit.first && d <= limit.last

    override fun solvePart2(): Long {
        val speeds = hailstones.map { it.first.x to it.second.x }.possibleSpeeds().flatMap { x ->
            hailstones.map { it.first.y to it.second.y }.possibleSpeeds().flatMap { y ->
                hailstones.map { it.first.z to it.second.z }.possibleSpeeds().map { z ->
                    PointD(x.toDouble(), y.toDouble(), z.toDouble())
                }
            }
        }
        return speeds.firstNotNullOf { speed ->
            rockSpeedToRockPosition(speed).takeIf { checkRock(it, speed) }
        }.run { x + y + z }.toLong()
    }

    private fun List<Pair<Double, Double>>.possibleSpeeds() = flatMapIndexed { index, first ->
        drop(index + 1).mapNotNull {
            if (it.second == first.second) it.second.toLong() to abs(it.first - first.first).toLong() else null
        }
    }.groupBy({ it.first }, { it.second }).mapValues { it.value.reduce { a, b -> gcd(a, b) } }
        .toList().sortedBy { it.second }.run {
            val max = minOf { abs(it.first) + it.second }
            (-max..max).filter { speed ->
                all { it.second.isDivideBy(speed - it.first) }
            }
        }

    private fun Long.isDivideBy(divider: Long) = divider != 0L && this % divider == 0L

    private fun checkRock(position: PointD, speed: PointD) =
        hailstones.map { (it.first - position) to (speed - it.second) }
            .all { it.first * it.second.x == it.second * it.first.x }

    private fun rockSpeedToRockPosition(rockSpeed: PointD): PointD {
        val lines = hailstones.take(2).map { it.first to it.second - rockSpeed }
        val point2D = intersect2D(lines[0], lines[1])!!
        val z = lines[0].run { first.z + second.z * (point2D.x - first.x) / second.x }
        return PointD(
            point2D.x.roundToLong().toDouble(),
            point2D.y.roundToLong().toDouble(),
            z.roundToLong().toDouble()
        )
    }

    // common

    private fun intersect2D(a: Pair<PointD, PointD>, b: Pair<PointD, PointD>): PointD? {
        val dv = a.second.x * b.second.y - b.second.x * a.second.y
        if (dv == 0.0) return null
        val u1 = a.run { first.x * second.y - first.y * second.x }
        val u2 = b.run { first.x * second.y - first.y * second.x }
        return PointD((a.second.x * u2 - b.second.x * u1), (a.second.y * u2 - b.second.y * u1)) / dv
    }

    // data

    private val regex = "(.+), (.+), (.+) @ (.+), (.+), (.+)".toRegex()
    private val limit = lines[0].split(" ").let { it[0].toLong()..it[1].toLong() }
    private val hailstones = lines.drop(1).map { line ->
        regex.matchEntire(line)!!.groupValues.drop(1).map { it.toDouble() }.let {
            PointD(it[0], it[1], it[2]) to PointD(it[3], it[4], it[5])
        }
    }
}
