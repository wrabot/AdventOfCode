package aoc2016

import Day
import tools.board.Board
import tools.match

class Day8(test: Int? = null) : Day(test) {
    override fun solvePart1() = code.cells.count { it == '#' }

    override fun solvePart2() = code.toString()

    sealed interface Instruction {
        data class Rect(val width: Int, val height: Int) : Instruction
        data class RotateRow(val row: Int, val pixels: Int) : Instruction
        data class RotateColumn(val column: Int, val pixels: Int) : Instruction
    }

    private val rectRegex = "rect (\\d+)x(\\d+)".toRegex()
    private val rowRegex = "rotate row y=(\\d+) by (\\d+)".toRegex()
    private val columnRegex = "rotate column x=(\\d+) by (\\d+)".toRegex()

    val instructions = lines.map { line ->
        val rect = line.match(rectRegex)
        val row = line.match(rowRegex)
        val column = line.match(columnRegex)
        when {
            rect != null -> Instruction.Rect(rect[0].toInt(), rect[1].toInt())
            row != null -> Instruction.RotateRow(row[0].toInt(), row[1].toInt())
            column != null -> Instruction.RotateColumn(column[0].toInt(), column[1].toInt())
            else -> error("impossible!!!")
        }
    }

    private val code = instructions.fold(Board(50, 6, List(300) { ' ' })) { acc, instruction ->
        Board(acc.width, acc.height, List(acc.cells.size) {
            val xy = acc.xy[it]
            when (instruction) {
                is Instruction.Rect -> if (xy.x < instruction.width && xy.y < instruction.height) '#' else acc.cells[it]
                is Instruction.RotateColumn -> if (xy.x == instruction.column) acc[xy.copy(y = (xy.y + acc.height - instruction.pixels) % acc.height)] else acc.cells[it]
                is Instruction.RotateRow -> if (xy.y == instruction.row) acc[xy.copy(x = (xy.x + acc.width - instruction.pixels) % acc.width)] else acc.cells[it]
            }
        })
    }
}
