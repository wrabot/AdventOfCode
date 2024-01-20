package aoc2023

import Day
import tools.board.Direction4
import tools.board.Direction4.*
import tools.geometry.Point
import kotlin.math.abs

class Day18b(test: Int? = null) : Day(test) {
    override fun solvePart1() = movesPart1.solve()

    override fun solvePart2() = movesPart2.solve()

    // "border area" + "shoelace area" = ((sum of border) + (sum of determinants)) / 2 + 1
    // "border area" = (sum of border) / 2 + 1 (because there are only 90° turn)
    //     "straight border" = 1/2, "convex turn" = 3/4, "concave turn" =  1/4
    //     as there are only 90° turn, there 4 convex turns more than concave turns because sum of angles must 360°
    //     "convex turn" + "concave turn" = 2 "straight border"
    //     so, "border area" => ((sum of border) - 4) / 2 + 4 * 3 / 4 => (sum of border) / 2 + 1
    // "shoelace area" = (sum of determinants) / 2
    private fun List<Point>.solve() = runningFold(Point.Zero) { acc, move -> acc + move }.zipWithNext().run {
        sumOf { (it.first - it.second).manhattan() } + abs(sumOf { it.first.x * it.second.y - it.first.y * it.second.x })
    }.toLong() / 2 + 1

    private val regex = "(.) (.*) \\(#(.*)\\)".toRegex()
    private val movesPart1 = lines.map { line ->
        val values = regex.matchEntire(line)!!.groupValues
        when (values[1]) {
            "U" -> North
            "D" -> South
            "R" -> East
            "L" -> West
            else -> error("unexpected direction")
        }.toPoint() * values[2].toDouble()
    }
    private val movesPart2 = lines.map { line ->
        val color = regex.matchEntire(line)!!.groupValues[3]
        when (color.last()) {
            '3' -> North
            '1' -> South
            '0' -> East
            '2' -> West
            else -> error("unexpected direction")
        }.toPoint() * color.dropLast(1).toInt(16).toDouble()
    }

    private fun Direction4.toPoint() = Point(xy.x, xy.y)
}
