package aoc2022

import Day

class Day4(test: Int? = null) : Day(2022, 4, test) {
    override fun solvePart1() = sections.count { it[0] in it[1] || it[1] in it[0] }
    override fun solvePart2() = sections.count { it[0].intersect(it[1]).isNotEmpty() }
    operator fun IntRange.contains(other: IntRange) = first <= other.first && last >= other.last
    private val sections = lines.map { line ->
        line.split(",").map { range ->
            range.split("-").let { (start, end) -> start.toInt()..end.toInt() }
        }
    }
}
