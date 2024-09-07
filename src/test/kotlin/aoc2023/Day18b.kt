package aoc2023

import Day
import tools.board.Direction4
import tools.board.Direction4.*
import tools.Point
import tools.geometry.Polygon

class Day18b(test: Int? = null) : Day(test) {
    override fun solvePart1() = movesPart1.solve()

    override fun solvePart2() = movesPart2.solve()

    // "border area" = perimeter / 2 + 1 (because there are only 90° turn)
    //     "straight border" = 1/2, "convex turn" = 3/4, "concave turn" =  1/4
    //     as there are only 90° turn, there 4 convex turns more than concave turns because sum of angles must 360°
    //     "convex turn" + "concave turn" = 2 "straight border"
    //     so, "border area" => (perimeter - 4) / 2 + 4 * 3 / 4 => perimeter / 2 + 1
    private fun List<Point>.solve() =
        Polygon(runningFold(Point.Zero) { acc, move -> acc + move }).run { perimeter / 2 + 1 + area }.toLong()

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
