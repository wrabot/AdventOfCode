package aoc2024

import Day
import kotlin.math.abs

class Day1(test: Int? = null) : Day(test) {
    private val lists = lines.map { line -> line.split("   ").map { it.toInt() } }
    private val left = lists.map { it[0] }
    private val right = lists.map { it[1] }

    override fun solvePart1() = left.sorted().zip(right.sorted()) { a, b -> abs(a - b) }.sum()

    override fun solvePart2(): Int {
        val count = right.groupingBy { it }.eachCount()
        return left.sumOf { it * count.getOrDefault(it, 0) }
    }
}
