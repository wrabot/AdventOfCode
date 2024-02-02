package aoc2019

import Day

class Day19(test: Int? = null) : Day(test) {
    override fun solvePart1() = part1range.sumOf { y -> part1range.count { x -> check(x, y) } }
    private val part1range = 0..49

    override fun solvePart2(): Int {
        var y = 100
        val xRanges = MutableList(100) { 0..100 }
        while (true) {
            y++
            val lastRange = xRanges.last()
            var firstX = lastRange.first()
            while (!check(firstX, y)) firstX++
            var lastX = lastRange.last()
            while (check(lastX, y)) lastX++
            xRanges.add(firstX..<lastX)
            xRanges.removeAt(0)
            if (xRanges.size == 100 && (xRanges.first().last - xRanges.last().first) == 99) break
        }
        return xRanges.last().first * 10000 + y - 99
    }

    private fun check(x: Int, y: Int) = Day9.Runtime(code).run {
        execute(x.toLong())
        execute(y.toLong())
    } == 1L

    private val code = lines.first().split(",").map { it.toLong() }
}


