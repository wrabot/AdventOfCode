package aoc2023

import tools.*
import tools.board.Direction.*
import tools.board.Board
import tools.board.Direction
import tools.board.Point

class Day18(test: Int? = null) : Day(2023, 18, test) {
    override fun solvePart1() = instructionsPart1.solve()

    override fun solvePart2() = instructionsPart2.solve()

    private fun List<Instruction>.solve(): Long {
        val points = runningFold(Point(0, 0)) { acc, instruction ->
            acc + instruction.direction.delta * instruction.length
        }
        val columns = points.map { it.x }.sorted().distinct()
        val rows = points.map { it.y }.sorted().distinct()
        val width = columns.size * 2 - 1
        val height = rows.size * 2 - 1
        val map = Board(width, height, List(width * height) { Cell('.') })
        val origin = Point(2 * columns.indexOf(0), 2 * rows.indexOf(0))

        map.points.forEach { point ->
            map[point].size = Point(
                if (point.x % 2 == 0) 1 else columns[point.x / 2 + 1] - columns[point.x / 2] - 1,
                if (point.y % 2 == 0) 1 else rows[point.y / 2 + 1] - rows[point.y / 2] - 1
            )
        }

        var position = origin
        map[position].c = '#'
        forEach { instruction ->
            var length = instruction.length
            while (length > 0) {
                position += instruction.direction.delta
                length -= when (instruction.direction) {
                    North, South -> map[position].size.y
                    East, West -> map[position].size.x
                    else -> error("unexpected direction")
                }
                map[position].c = '#'
            }
        }

        return (map.zone4(origin + Point(1, 1)) { map[it].c == '.' } + map.points.filter { map[it].c == '#' }).sumOf {
            map[it].size.run { x.toLong() * y }
        }
    }

    data class Cell(var c: Char) {
        var size: Point = Point(1, 1)
        override fun toString() = c.toString()
    }

    data class Instruction(val direction: Direction, val length: Int)

    private val regex = "(.) (.*) \\(#(.*)\\)".toRegex()
    private val instructionsPart1 = lines.map { line ->
        val values = regex.matchEntire(line)!!.groupValues
        Instruction(
            when (values[1]) {
                "U" -> North
                "D" -> South
                "R" -> East
                "L" -> West
                else -> error("unexpected direction")
            },
            values[2].toInt()
        )
    }
    private val instructionsPart2 = lines.map { line ->
        val color = regex.matchEntire(line)!!.groupValues[3]
        Instruction(
            when (color.last()) {
                '3' -> North
                '1' -> South
                '0' -> East
                '2' -> West
                else -> error("unexpected direction")
            },
            color.dropLast(1).toInt(16)
        )
    }
}
