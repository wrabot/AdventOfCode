package aoc2016

import Day

class Day18(val test: Int? = null) : Day(test) {
    override fun solvePart1() = solve(if (test == null) 40 else 10)
    override fun solvePart2() = solve(400000)

    private fun solve(size: Int): Int {
        var row = input.map { it == '^' }
        var count = row.count { !it }
        repeat(size - 1) {
            row = row.indices.map { (row.getOrNull(it - 1) ?: false) xor (row.getOrNull(it + 1) ?: false) }
            count += row.count { !it }
        }
        return count
    }
}
