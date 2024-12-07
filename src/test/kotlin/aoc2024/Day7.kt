package aoc2024

import Day

class Day7(test: Int? = null) : Day(test) {
    private val equations = lines.map { line -> line.split(": ", " ").map { it.toLong() } }
    override fun solvePart1() = equations.filter {
        calibrate1(it[0], it[1], it.drop(2))
    }.sumOf { it[0] }

    private fun calibrate1(total: Long, current: Long, values: List<Long>): Boolean {
        if (values.isEmpty()) return total == current
        val next = values[0]
        val remaining = values.drop(1)
        return calibrate1(total, current + next, remaining) ||
                calibrate1(total, current * next, remaining)
    }

    override fun solvePart2() = equations.filter {
        calibrate2(it[0], it[1], it.drop(2))
    }.sumOf { it[0] }

    private fun calibrate2(total: Long, current: Long, values: List<Long>): Boolean {
        if (values.isEmpty()) return total == current
        val next = values[0]
        val remaining = values.drop(1)
        return calibrate2(total, current + next, remaining) ||
                calibrate2(total, current * next, remaining) ||
                calibrate2(total, (current.toString() + next.toString()).toLong(), remaining)
    }
}
