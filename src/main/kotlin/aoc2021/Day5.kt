package aoc2021

import tools.Day
import tools.Point

class Day5(test: Int? = null) : Day(2021, 5, test) {
    override fun getPart1() = part1
    override fun getPart2() = part2

    private val segments = lines.map { line ->
        val (start, end) = line.split(" -> ").map { point ->
            val (x, y) = point.split(",").map { it.toInt() }
            Point(x, y)
        }
        start to end
    }

    private val part1: Int
    private val part2: Int

    init {
        val board = mutableMapOf<Point, Int>()
        segments.forEach { segment ->
            if (segment.first.x == segment.second.x) {
                range(segment.first.y, segment.second.y).forEach { board.add(Point(segment.first.x, it)) }
            } else if (segment.first.y == segment.second.y) {
                range(segment.first.x, segment.second.x).forEach { board.add(Point(it, segment.first.y)) }
            }
        }
        part1 = board.count { it.value > 1 }
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
        part2 = board.count { it.value > 1 }
    }

    private fun MutableMap<Point, Int>.add(point: Point) = put(point, getOrDefault(point, 0) + 1)

    private fun range(start: Int, end: Int) = if (start < end) start..end else end..start
}
