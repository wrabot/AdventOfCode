package aoc2018

import Day
import tools.Point
import tools.toPoint
import tools.match


class Day23(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        val max = nanobots.maxBy { it.radius }
        return nanobots.count { max.inRange(it.position) }
    }

    override fun solvePart2() = nanobots.flatMap { it.bots() }.maxBy { it.size }.maxOf { it.lowestManhattan }
    private fun Nanobot.bots() = points6.map { p -> nanobots.filter { it.inRange(p) } }

    private data class Nanobot(val position: Point, val radius: Double) {
        val lowestManhattan = (position.manhattan() - radius).toInt()
        val points6 = listOf(Point(x = 1), Point(x = -1), Point(y = 1), Point(y = -1), Point(z = 1), Point(z = -1))
            .map { position + it * radius }

        fun inRange(point: Point) = (point - position).manhattan() <= radius
    }

    private val regex = "pos=<(.*)>, r=(.*)".toRegex()
    private val nanobots = lines.map {
        val (center, radius) = it.match(regex)!!
        Nanobot(center.toPoint(","), radius.toDouble())
    }
}
