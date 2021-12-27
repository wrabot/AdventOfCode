package aoc2021

import tools.Board
import forEachInput
import tools.log

object Day20 {
    fun solve() = forEachInput(2021, 20, 1, 2) { lines ->
        val algo = lines[0]
        var image = createImage(lines.drop(2), '.')

        log("part 1: ")
        repeat(2) { image = image.enhance(algo) }
        image.displayResult()

        log("part 2: ")
        repeat(48) { image = image.enhance(algo) }
        image.displayResult()
    }

    private fun createImage(rows: List<String>, outside: Char): Board<Char> {
        val width = rows[0].length + 2
        val separator = outside.toString().repeat(width).toList()
        return Board(width, rows.size + 2, separator + rows.flatMap { "$outside$it$outside".toList() } + separator)
    }

    private fun Board<Char>.displayResult() = cells.count { it == '#' }.log()

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
