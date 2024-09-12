package aoc2019

import Day
import tools.geometry.Line
import tools.Point
import tools.toPoint
import tools.text.match
import tools.math.lcm
import kotlin.math.sign

class Day12(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        val moons = initial.toTypedArray()
        repeat(repeat) { step(moons) }
        return moons.sumOf { it.origin.manhattan() * it.vector.manhattan() }.toInt()
    }

    override fun solvePart2(): Long {
        val moons = initial.toTypedArray()
        val axes = listOf(Point::x, Point::y, Point::z).map {
            Axe(it, mutableMapOf(moons.key(it) to 0L))
        }
        var count = 0L
        while (axes.any { it.period == null }) {
            step(moons)
            count++
            axes.forEach {
                if (it.period == null) {
                    val key = moons.key(it.axe)
                    val value = it.cache[key]
                    if (value == null) it.cache[key] = count else it.period = count - value
                }
            }
        }
        return axes.mapNotNull { it.period }.reduce { acc, l -> lcm(acc, l) }
    }

    private data class Axe(
        val axe: Point.() -> Double,
        val cache: MutableMap<List<Double>, Long>,
        var period: Long? = null
    )

    fun Array<Line>.key(axe: Point.() -> Double) = flatMap { listOf(it.origin.axe(), it.vector.axe()) }

    // common

    private fun step(moons: Array<Line>) {
        val gravity = moons.map { moon ->
            moons.map { it.origin - moon.origin }.map { Point(it.x.sign, it.y.sign, it.z.sign) }
                .reduce { acc, point -> acc + point }
        }
        moons.forEachIndexed { index, moon ->
            val velocity = moon.vector + gravity[index]
            moons[index] = Line(moon.origin + velocity, velocity)
        }
    }

    private val repeat = lines[0].toInt()
    private val initial = lines.drop(1).map { line ->
        line.match("<x=(.*), y=(.*), z=(.*)>".toRegex())!!.joinToString(" ").toPoint(" ")
    }.map { Line(it, Point.Zero) }
}
