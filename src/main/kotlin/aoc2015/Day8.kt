package aoc2015

import forEachInput
import tools.log

object Day8 {
    fun solve() = forEachInput(2015, 8, 2) { lines ->
        val hexa = "\\\\x(..)".toRegex()

        log("part 1: ")
        (lines.sumOf { it.length } - lines.map {
            it.substring(1, it.length - 1).replace("\\\"", "\"").split("\\\\").joinToString("\\") {
                it.replace(hexa) {
                    it.groupValues[1].toInt(16).toChar().toString()
                }
            }
        }.sumOf { it.length }).log()

        log("part 2: ")
        (lines.map { it.replace("\\", "\\\\").replace("\"", "\\\"") }.sumOf { it.length + 2 } - lines.sumOf { it.length }).log()
    }
}
