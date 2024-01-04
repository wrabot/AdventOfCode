package aoc2023

import Day
import tools.board.Direction.*
import tools.board.Board
import tools.board.Direction
import tools.board.Point
import kotlin.math.max

class Day16(test: Int? = null) : Day(2023, 16, test) {
    override fun solvePart1() = countBeams(0, 0, East)

    override fun solvePart2() = max(
        (0..<map.height).maxOf { max(countBeams(0, it, East), countBeams(map.width - 1, it, West)) },
        (0..<map.width).maxOf { max(countBeams(it, 0, South), countBeams(it, map.height - 1, North)) },
    )

    private fun countBeams(x: Int, y: Int, direction: Direction): Int {
        map.cells.forEach { it.incomingBeams.clear() }
        val todo = mutableListOf(Beam(Point(x, y), direction))
        while (true) {
            val beam = todo.removeFirstOrNull() ?: break
            val cell = map.getOrNull(beam.point) ?: continue
            if (beam.direction in cell.incomingBeams) continue
            cell.incomingBeams.add(beam.direction)
            cell.outgoing(beam.direction) {
                todo.add(Beam(beam.point + it.delta, it))
            }
        }
        return map.cells.count { it.incomingBeams.isNotEmpty() }
    }

    data class Beam(val point: Point, val direction: Direction)

    data class Cell(val c: Char) {
        val incomingBeams = mutableSetOf<Direction>()

        override fun toString() = when {
            c != '.' || incomingBeams.size == 0 -> c
            incomingBeams.size == 1 -> incomingBeams.first().c
            else -> incomingBeams.size
        }.toString()

        fun outgoing(incoming: Direction, block: (Direction) -> Unit) {
            when (c) {
                '.' -> block(incoming)
                '\\' -> when (incoming) {
                    North -> block(West)
                    South -> block(East)
                    East -> block(South)
                    West -> block(North)
                    else -> error("unexpected direction")
                }

                '/' -> when (incoming) {
                    North -> block(East)
                    South -> block(West)
                    East -> block(North)
                    West -> block(South)
                    else -> error("unexpected direction")
                }

                '|' -> when (incoming) {
                    North, South -> block(incoming)
                    East, West -> {
                        block(South)
                        block(North)
                    }

                    else -> error("unexpected direction")
                }

                '-' -> when (incoming) {
                    East, West -> block(incoming)
                    South, North -> {
                        block(West)
                        block(East)
                    }

                    else -> error("unexpected direction")
                }

                else -> error("unexpected cell")
            }
        }
    }

    private val map = Board(lines[0].length, lines.size, lines.flatMap { line -> line.map { Cell(it) } })
}
