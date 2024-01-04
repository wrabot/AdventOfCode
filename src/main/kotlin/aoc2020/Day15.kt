package aoc2020

import Day

class Day15(test: Int? = null) : Day(2020, 15, test) {
    override fun solvePart1() = numbers.map { it.memory(2020) }
    override fun solvePart2() = numbers.map { it.memory(30000000) }

    private val numbers = lines.map { line -> line.split(",").map { it.toInt() } }

    private fun List<Int>.memory(turns: Int): Int {
        val numbers = IntArray(turns) { 0 }
        dropLast(1).forEachIndexed { index, n -> numbers[n] = index + 1 }
        var last = last()
        (size until turns).forEach {
            last = numbers[last].apply { numbers[last] = it }.run { if (this == 0) 0 else it - this }
        }
        return last
    }
}
