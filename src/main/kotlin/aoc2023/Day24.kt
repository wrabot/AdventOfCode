package aoc2023

import tools.Day
import tools.board.LineD
import tools.board.PointD
import tools.math.gcd
import kotlin.math.abs

class Day24(test: Int? = null) : Day(2023, 24, test) {
    override fun solvePart1() = hailstones.mapIndexed { index, hailstone ->
        hailstones.drop(index + 1).count {
            val t = hailstone.intersectXY(it) ?: return@count false
            val point = hailstone[t]
            val t2 = (point.x - it.origin.x) / it.vector.x
            (t >= 0 && t2 >= 0 && inLimit(point.x) && inLimit(point.y))
        }
    }.sum()

    private fun inLimit(d: Double) = d >= limit.first && d <= limit.last

    override fun solvePart2(): Long {
        val speeds = hailstones.map { it.origin.x to it.vector.x }.possibleSpeeds().flatMap { x ->
            hailstones.map { it.origin.y to it.vector.y }.possibleSpeeds().flatMap { y ->
                hailstones.map { it.origin.z to it.vector.z }.possibleSpeeds().map { z ->
                    PointD(x.toDouble(), y.toDouble(), z.toDouble())
                }
            }
        }
        return speeds.firstNotNullOf { speed ->
            rockSpeedToRockPosition(speed).takeIf { checkRock(it, speed) }
        }.run { x + y + z }.toLong()
    }

    // for an axe x, y or z
    // Pr + Vr * t = Pi + Vi * t => Pr = Pi + (Vi - Vr) * t
    // for identical speed V, Pj-Pi = (Vr-V)*k, V-Vr divide Pj-Pi
    private fun List<Pair<Double, Double>>.possibleSpeeds() = flatMapIndexed { index, one ->
        drop(index + 1).mapNotNull { other ->
            if (one.second == other.second) one.second.toLong() to abs(one.first - other.first).toLong() else null
        }
    }.groupBy({ it.first }, { it.second }) // V to list of abs(Pj-Pi)
        .mapValues { it.value.reduce { a, b -> gcd(a, b) } } // V-Vr divide Pj-Pi => V-Vr divide gcd(Pj-Pi)
        .toList().sortedBy { it.second }.run {
            val max = minOf { abs(it.first) + it.second }
            (-max..max).filter { speed ->
                all { it.second.isDivideBy(speed - it.first) }
            }
        }

    private fun Long.isDivideBy(divider: Long) = divider != 0L && this % divider == 0L

    // Pr + Vr * t = Pi + Vi * t => Pi - Pr = (Vi - Vr) * t where t = (Pi - Pr).x/Vi-V.x
    // (Pi - Pr)*Vi.x = (Vi - Vr) * Pi.x/Vi.x
    private fun checkRock(position: PointD, speed: PointD) =
        hailstones.map { (it.origin - position) to (speed - it.vector) }
            .all { it.first * it.second.x == it.second * it.first.x }

    // Pr + Vr * t = Pi + Vi * t => Pr = Pi + (Vi - Vr) * t
    // Pr is intersection (P0, V0-Vr) and (P1, V1-Vr)
    private fun rockSpeedToRockPosition(rockSpeed: PointD): PointD {
        val lines = hailstones.take(2).map { LineD(it.origin, it.vector - rockSpeed) }
        return lines[0][lines[0].intersectXY(lines[1])!!]
    }

    // data

    private val regex = "(.+), (.+), (.+) @ (.+), (.+), (.+)".toRegex()
    private val limit = lines[0].split(" ").let { it[0].toLong()..it[1].toLong() }
    private val hailstones = lines.drop(1).map { line ->
        regex.matchEntire(line)!!.groupValues.drop(1).map { it.toDouble() }.let {
            LineD(PointD(it[0], it[1], it[2]), PointD(it[3], it[4], it[5]))
        }
    }
}
