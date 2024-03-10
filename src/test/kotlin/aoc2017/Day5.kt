package aoc2017

import Day

class Day5(test: Int? = null) : Day(test) {
    override fun solvePart1() = solve(false)
    override fun solvePart2() = solve(true)

    fun solve(decrease: Boolean): Int {
        var steps = 0
        var offset = 0
        val memory = jumps.toIntArray()
        while (offset < memory.size) {
            offset += memory[offset].also { if (decrease && it >= 3) memory[offset]-- else memory[offset]++ }
            steps++
        }
        return steps
    }

    private val jumps = lines.map { it.toInt() }
}
