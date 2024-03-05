package aoc2016

import Day
import tools.match
import tools.math.bi
import tools.math.chineseRemainder

class Day15(test: Int? = null) : Day(test) {
    override fun solvePart1() = solve(disks)
    override fun solvePart2() = solve(disks + (11 to 0))

    private fun solve(disks: List<Pair<Int, Int>>) =
        chineseRemainder(disks.mapIndexed { i, p -> -(p.second + i + 1).bi to p.first.bi })

    val regex = "Disc #\\d+ has (\\d+) positions; at time=0, it is at position (\\d+).".toRegex()
    val disks = lines.map { it.match(regex)!!.run { first().toInt() to last().toInt() } }
}
