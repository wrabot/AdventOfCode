package aoc2022

import Day
import tools.board.Board
import tools.board.Direction8.*
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

    private val sandFall = Board.XY(500, 0)
    private val directions = listOf(South, SouthWest, SouthEast)

    private fun sendSand(goingOn: (Board.XY) -> Boolean) {
        var sand = sandFall
        while (goingOn(sand)) {
            sand = directions.map { sand + it.xy }.find { cave[it].content == '.' } ?: sandFall.apply {
                sandCount++
                cave[sand].content = 'O'
            }
        }
    }

    private var sandCount = 0
    private val cave: Board<Cell>

    private data class Cell(var content: Char) {
        override fun toString() = content.toString()
    }

    init {
        val lines = lines.map { line ->
            line.split(" -> ").map { xy ->
                val (x, y) = xy.split(",").map { it.toInt() }
                Board.XY(x, y)
            }
        }
        val height = lines.flatten().run { maxOf { it.y } } + 3
        val width = 500 + height
        cave = Board(width, height, List(width * height) { Cell('.') })
        val rocks = lines.flatMap { it.zipWithNext() }.flatMap { line ->
            when {
                line.first.x == line.second.x ->
                    (min(line.first.y, line.second.y)..max(line.first.y, line.second.y)).map {
                        Board.XY(line.first.x, it)
                    }

                line.first.y == line.second.y ->
                    (min(line.first.x, line.second.x)..max(line.first.x, line.second.x)).map {
                        Board.XY(it, line.first.y)
                    }

                else -> error("invalid input")
            }
        }.distinct()
        rocks.forEach { cave[it].content = '#' }
    }
}
