package aoc2023

import Day
import tools.board.Board
import tools.board.Direction4
import tools.board.Direction4.*
import tools.XY
import kotlin.math.max

class Day16(test: Int? = null) : Day(test) {
    override fun solvePart1() = countBeams(0, 0, East)

    override fun solvePart2() = max(
        (0..<map.height).maxOf { max(countBeams(0, it, East), countBeams(map.width - 1, it, West)) },
        (0..<map.width).maxOf { max(countBeams(it, 0, South), countBeams(it, map.height - 1, North)) },
    )

    private fun countBeams(x: Int, y: Int, direction: Direction4): Int {
        map.cells.forEach { it.incomingBeams.clear() }
        val todo = mutableListOf(Beam(XY(x, y), direction))
        while (true) {
            val beam = todo.removeFirstOrNull() ?: break
            val cell = map.getOrNull(beam.point) ?: continue
            if (beam.direction in cell.incomingBeams) continue
            cell.incomingBeams.add(beam.direction)
            cell.outgoing(beam.direction) {
                todo.add(Beam(beam.point + it.xy, it))
            }
        }
        return map.cells.count { it.incomingBeams.isNotEmpty() }
    }

    private data class Beam(val point: XY, val direction: Direction4)

    private data class Cell(val c: Char) {
        val incomingBeams = mutableSetOf<Direction4>()

        override fun toString() = when {
            c != '.' || incomingBeams.size == 0 -> c
            incomingBeams.size == 1 -> incomingBeams.first().c
            else -> incomingBeams.size
        }.toString()

        fun outgoing(incoming: Direction4, block: (Direction4) -> Unit) {
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
