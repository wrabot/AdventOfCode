package aoc2018

import Day
import tools.geometry.Point
import tools.match
import kotlin.math.round

class Day10(test: Int? = null) : Day(test) {
    override fun solvePart1() = points.map { it.first + it.second * best }.toBoard()

    fun List<Point>.toBoard(): String {
        val (minX, maxX) = map { it.x }.run { min().toInt() to max().toInt() }
        val (minY, maxY) = map { it.y }.run { min().toInt() to max().toInt() }
        val width = maxX - minX + 1
        val height = maxY - minY + 1
        val cells = List(height) { StringBuilder(".".repeat(width)) }
        forEach { cells[it.y.toInt() - minY][it.x.toInt() - minX] = '#' }
        return cells.joinToString("\n")
    }

    override fun solvePart2() = best.toInt()

    private val regex = "position=< *(.*), *(.*)> velocity=< *(.*), *(.*)>".toRegex()
    private val points = lines.map {
        val (px, py, vx, vy) = it.match(regex)!!
        Point(px.toInt(), py.toInt()) to Point(vx.toInt(), vy.toInt())
    }

    // compute best T with standard deviation
    // move points relative to average point and find min with standard deviation formula
    // find T for min of sumOf{ (p+vT)^2 } => sumOf{(px + vx*T)^2+(py + vy*T)^2} =>
    // find T for  0 for sumOf{ 2*(px + vx*T)*vx+2*(py + vy*T)*vy }
    // T = -sumOf{px*vx+py*vy}/sumOf{vx*vx+vy*vy}
    // T = -sumOf{p * v}/sumOf{|v|^2}
    private val average = points.reduce { acc, pair -> Pair(acc.first + pair.first, acc.second + pair.second) }.run {
        first / points.size.toDouble() to second / points.size.toDouble()
    }
    private val relative = points.map { Pair(it.first - average.first, it.second - average.second) }
    private val best = round(-relative.sumOf { it.first * it.second } / relative.sumOf { it.second.norm2() })
}
