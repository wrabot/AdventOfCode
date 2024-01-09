package aoc2021

import tools.board.Board
import Day

class Day20(test: Int? = null) : Day(test) {
    override fun solvePart1(): Any {
        repeat(2) { image = image.enhance(algo) }
        return image.litPixelCount()
    }

    override fun solvePart2(): Any {
        part1 // force part1
        repeat(50 - 2) { image = image.enhance(algo) }
        return image.litPixelCount()
    }

    private val algo = lines[0]
    private var image = createImage(lines.drop(2), '.')

    private fun createImage(rows: List<String>, outside: Char): Board<Char> {
        val width = rows[0].length + 2
        val separator = outside.toString().repeat(width).toList()
        return Board(width, rows.size + 2, separator + rows.flatMap { "$outside$it$outside".toList() } + separator)
    }

    private fun Board<Char>.litPixelCount() = cells.count { it == '#' }

    private fun Board<Char>.enhance(algo: String): Board<Char> {
        val outside = cells[0]
        val rows = points.map { point ->
            (-1..1).flatMap { dy -> (-1..1).map { dx -> getOrNull(point.x + dx, point.y + dy) ?: outside } }
                .joinToString("") { if (it == '#') "1" else "0" }
                .toInt(2)
                .let { algo[it] }
        }.chunked(width) { it.joinToString("") }
        return createImage(rows, if ((algo[0] == '#') xor (outside == '#')) '#' else '.')
    }
}
