package aoc2017

import Day
import tools.sequence.select
import tools.toWords

class Day2(test: Int? = null) : Day(test) {
    override fun solvePart1() = rows.sumOf { it.last() - it.first() }
    override fun solvePart2() = rows.sumOf { r -> r.select(2).first { it[1] % it[0] == 0 }.let { it[1] / it[0] } }
    val rows = lines.map { line -> line.toWords().map { it.toInt() }.sorted() }
}
