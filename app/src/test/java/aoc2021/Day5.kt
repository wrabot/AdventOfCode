package aoc2021

import tools.Point
import forEachInput
import tools.log

object Day5 {
    fun solve() = forEachInput(2021, 5, 1, 2) { lines ->
        val segments = lines.map { line ->
            val (start, end) = line.split(" -> ").map { point ->
                val (x, y) = point.split(",").map { it.toInt() }
                Point(x, y)
            }
            start to end
        }

        val board = mutableMapOf<Point, Int>()
        fun MutableMap<Point, Int>.add(point: Point) = put(point, getOrDefault(point, 0) + 1)
        fun range(start: Int, end: Int) = if (start < end) start..end else end..start

        log("part 1: ")
        segments.forEach { segment ->
            if (segment.first.x == segment.second.x) {
                range(segment.first.y, segment.second.y).forEach { board.add(Point(segment.first.x, it)) }
            } else if (segment.first.y == segment.second.y) {
                range(segment.first.x, segment.second.x).forEach { board.add(Point(it, segment.first.y)) }
            }
        }
        board.count { it.value > 1 }.log()

        log("part 2: ")
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
        board.count { it.value > 1 }.log()
    }
}
