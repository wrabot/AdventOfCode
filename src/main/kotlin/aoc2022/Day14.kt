package aoc2022

import tools.board.Board
import Day
import tools.board.Point
import kotlin.math.max
import kotlin.math.min

class Day14(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        sendSand { it.y < cave.height - 1 }
        return sandCount
    }

    override fun solvePart2(): Int {
        for (x in 0 until cave.width) {
            cave[x, cave.height - 1].content = '#'
        }
        sendSand { cave[sandFall].content == '.' }
        return sandCount
    }

    private val sandFall = Point(500, 0)
    private val directions = listOf(Point(0, 1), Point(-1, 1), Point(1, 1))

    private fun sendSand(goingOn: (Point) -> Boolean) {
        var sand = sandFall
        while (goingOn(sand)) {
            sand = directions.map { sand + it }.find { cave[it].content == '.' } ?: sandFall.apply {
                sandCount++
                cave[sand].content = 'O'
            }
        }
        //cave.toString(Point(500 - cave.height, 0), Point(500 + cave.height - 1, cave.height - 1)).log()
    }

    private var sandCount = 0
    private val cave: Board<Cell>

    data class Cell(var content: Char) {
        override fun toString() = content.toString()
    }

    init {
        val lines = lines.map { line ->
            line.split(" -> ").map { point ->
                val (x, y) = point.split(",").map { it.toInt() }
                Point(x, y)
            }
        }
        val height = lines.flatten().run { maxOf { it.y } } + 3
        val width = 500 + height
        cave = Board(width, height, List(width * height) { Cell('.') })
        val rocks = lines.flatMap { it.zipWithNext() }.flatMap { line ->
            when {
                line.first.x == line.second.x ->
                    (min(line.first.y, line.second.y)..max(line.first.y, line.second.y)).map {
                        Point(line.first.x, it)
                    }
                line.first.y == line.second.y ->
                    (min(line.first.x, line.second.x)..max(line.first.x, line.second.x)).map {
                        Point(it, line.first.y)
                    }
                else -> error("invalid input")
            }
        }.distinct()
        rocks.forEach { cave[it].content = '#' }
    }
}
