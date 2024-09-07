package aoc2019

import Day
import tools.Point
import kotlin.math.atan2

class Day10(test: Int? = null) : Day(test) {
    override fun solvePart1() = best.value

    override fun solvePart2(): Int {
        var point: Point? = null
        val targets = asteroids.asSequence().minus(best.key).groupBy { (it - best.key).run { atan2(-x, y) } }.toList()
            .sortedBy { it.first }.map { entry -> entry.second.sortedBy { (it - best.key).manhattan() } }
        var angle = 0
        var rank = 0
        repeat(200) {
            while (true) {
                point = targets[angle].getOrNull(rank)
                if (angle++ == targets.size) {
                    angle = 0
                    rank++
                }
                if (point != null) break
            }
        }
        return point!!.let { it.x * 100 + it.y }.toInt()
    }

    private val asteroids = lines.flatMapIndexed { y, line ->
        line.mapIndexedNotNull { x, c -> if (c == '#') Point(x, y) else null }
    }

    private val best = asteroids.associateWith { asteroid ->
        val vectors = asteroids.minus(asteroid).map { it - asteroid }
        vectors.count { v1 -> !vectors.minus(v1).any { v2 -> v1.isHiddenBy(v2) } }
    }.maxBy { it.value }

    private fun Point.isAligned(p: Point) = x * p.y == y * p.x
    private fun Point.isHiddenBy(p: Point) = isAligned(p) &&
            (p.x in 1.0..x || p.x in x..-1.0 || p.y in 1.0..y || p.y in y..-1.0)
}
