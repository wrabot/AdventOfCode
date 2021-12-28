package aoc2021

import tools.Board
import tools.Day

class Day20(test: Int? = null) : Day(2021, 20, test) {
    override fun getPart1() = part1
    override fun getPart2() = part2

    private val part1: Any
    private val part2: Any

    init {
        val algo = lines[0]
        var image = createImage(lines.drop(2), '.')

        repeat(2) { image = image.enhance(algo) }
        part1 = image.litPixelCount()

        repeat(48) { image = image.enhance(algo) }
        part2 = image.litPixelCount()
    }

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
