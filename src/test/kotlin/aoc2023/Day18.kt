package aoc2023

import Day
import tools.board.Board
import tools.board.Direction4
import tools.board.Direction4.*
import tools.board.XY

class Day18(test: Int? = null) : Day(test) {
    override fun solvePart1() = instructionsPart1.solve()

    override fun solvePart2() = instructionsPart2.solve()

    private fun List<Instruction>.solve(): Long {
        val xyList = runningFold(XY(0, 0)) { acc, instruction ->
            acc + instruction.direction.xy * instruction.length
        }
        val columns = xyList.map { it.x }.sorted().distinct()
        val rows = xyList.map { it.y }.sorted().distinct()
        val width = columns.size * 2 - 1
        val height = rows.size * 2 - 1
        val map = Board(width, height, List(width * height) { Cell('.') })
        val origin = XY(2 * columns.indexOf(0), 2 * rows.indexOf(0))

        map.xy.forEach { xy ->
            map[xy].size = XY(
                if (xy.x % 2 == 0) 1 else columns[xy.x / 2 + 1] - columns[xy.x / 2] - 1,
                if (xy.y % 2 == 0) 1 else rows[xy.y / 2 + 1] - rows[xy.y / 2] - 1
            )
        }

        var position = origin
        map[position].c = '#'
        forEach { instruction ->
            var length = instruction.length
            while (length > 0) {
                position += instruction.direction.xy
                length -= when (instruction.direction) {
                    North, South -> map[position].size.y
                    East, West -> map[position].size.x
                    else -> error("unexpected direction")
                }
                map[position].c = '#'
            }
        }

        return (map.zone4(origin + XY(1, 1)) { map[it].c == '.' } + map.xy.filter { map[it].c == '#' }).sumOf {
            map[it].size.run { x.toLong() * y }
        }
    }

    private data class Cell(var c: Char) {
        var size: XY = XY(1, 1)
        override fun toString() = c.toString()
    }

    private data class Instruction(val direction: Direction4, val length: Int)

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
