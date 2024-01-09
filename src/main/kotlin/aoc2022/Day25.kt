package aoc2022

import Day

class Day25(test: Int? = null) : Day(test) {
    override fun solvePart1() = lines.sumOf { it.fromSNAFU() }.toSNAFU()
    override fun solvePart2() = Unit

    private val table = listOf('=', '-', '0', '1', '2')
    private fun String.fromSNAFU() = fold(0L) { acc, c -> acc * 5 + table.indexOf(c) - 2 }
    private fun Long.toSNAFU(): String {
        var result = ""
        var value = this
        while (value > 0) {
            value += 2
            result += table[(value % 5).toInt()]
            value /= 5
        }
        return if (result.isEmpty()) "0" else result.reversed()
    }
}
