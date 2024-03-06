package aoc2016

import Day
import tools.merge

class Day20(test: Int? = null) : Day(test) {
    override fun solvePart1() = blacklist.first().last + 1
    override fun solvePart2() = blacklist.fold(0L to 0L) { acc, range ->
        range.last + 1 to acc.second + range.first - acc.first
    }.second

    private val blacklist = lines.map {
        val (first, last) = it.split("-")
        first.toLong()..last.toLong()
    }.merge()
}
