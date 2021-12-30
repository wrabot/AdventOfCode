package aoc2015

import tools.Day

class Day8(test: Int? = null) : Day(2015, 8, test) {
    override fun solvePart1() = lines.sumOf { it.length } - lines.map { line ->
        line.substring(1, line.length - 1)
            .replace("\\\"", "\"")
            .split("\\\\")
            .joinToString("\\") { part ->
                part.replace(regex) { it.groupValues[1].toInt(16).toChar().toString() }
            }
    }.sumOf { it.length }

    override fun solvePart2() = lines.map { it.replace("\\", "\\\\").replace("\"", "\\\"") }
        .sumOf { it.length + 2 } - lines.sumOf { it.length }

    private val regex = "\\\\x(..)".toRegex()
}
