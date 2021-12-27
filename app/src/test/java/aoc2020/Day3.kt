package aoc2020

import forEachInput
import tools.log

object Day3 {
    fun solve() = forEachInput(2020, 3, 1) { lines ->
        val width = lines[0].length
        val height = lines.size
        val map = lines.joinToString("")

        log("part 1: ")
        map.slope(width, height, 3, 1).log()

        log("part 2: ")
        listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2).map { map.slope(width, height, it.first, it.second) }
            .reduce { acc, i -> acc * i }.log()
    }

    private fun String.slope(width: Int, height: Int, dx: Int, dy: Int) =
        (0 until height step dy).count { this[it * width + (it / dy * dx) % width] == '#' }
}
