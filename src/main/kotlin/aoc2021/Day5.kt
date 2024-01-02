package aoc2021

import tools.Day
import tools.board.Point

class Day5(test: Int? = null) : Day(2021, 5, test) {
    override fun solvePart1(): Any {
        segments.forEach { segment ->
            if (segment.first.x == segment.second.x) {
                range(segment.first.y, segment.second.y).forEach { board.add(Point(segment.first.x, it)) }
            } else if (segment.first.y == segment.second.y) {
                range(segment.first.x, segment.second.x).forEach { board.add(Point(it, segment.first.y)) }
            }
        }
        return board.count { it.value > 1 }
    }

    override fun solvePart2(): Any {
        part1 // force part1
        segments.forEach { segment ->
            val width = segment.second.x - segment.first.x
            val height = segment.second.y - segment.first.y
            if (width == height || width == -height) {
                if (width > 0 && height > 0) {
                    (0..width).forEach { board.add(Point(segment.first.x + it, segment.first.y + it)) }
                } else if (width > 0 && height < 0) {
                    (0..width).forEach { board.add(Point(segment.first.x + it, segment.first.y - it)) }
                } else if (width < 0 && height > 0) {
                    (0..-width).forEach { board.add(Point(segment.first.x - it, segment.first.y + it)) }
                } else if (width < 0 && height < 0) {
                    (0..-width).forEach { board.add(Point(segment.first.x - it, segment.first.y - it)) }
                }
            }
        }
        return board.count { it.value > 1 }
    }

    private val segments = lines.map { line ->
        val (start, end) = line.split(" -> ").map { point ->
            val (x, y) = point.split(",").map { it.toInt() }
            Point(x, y)
        }
        start to end
    }

    private val board = mutableMapOf<Point, Int>()

    private fun MutableMap<Point, Int>.add(point: Point) = put(point, getOrDefault(point, 0) + 1)

    private fun range(start: Int, end: Int) = if (start < end) start..end else end..start
}
