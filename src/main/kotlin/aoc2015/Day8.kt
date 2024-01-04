package aoc2015

import Day

class Day8(test: Int? = null) : Day(2015, 8, test) {
    override fun solvePart1() = lines.sumOf { line ->
        line.length - line.substring(1, line.length - 1)
            .replace("\\\"", "\"")
            .split("\\\\")
            .joinToString("\\") { part ->
                part.replace(regex) { it.groupValues[1].toInt(16).toChar().toString() }
            }.length
    }

    override fun solvePart2() = lines.sumOf {
        it.replace("\\", "\\\\").replace("\"", "\\\"").length + 2 - it.length
    }

    private val regex = "\\\\x(..)".toRegex()
}
