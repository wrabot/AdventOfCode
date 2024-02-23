package aoc2016

import Day

class Day6(test: Int? = null) : Day(test) {
    override fun solvePart1() = String(columns.map { column -> column.maxBy { it.value }.key }.toCharArray())
    override fun solvePart2() = String(columns.map { column -> column.minBy { it.value }.key }.toCharArray())
    private val columns = lines[0].indices.map { column -> lines.groupingBy { it[column] }.eachCount() }
}
