package aoc2020

import tools.Day

class Day1 : Day(2020, 1) {
    override fun solvePart1() = numbers.product(2020)!!

    override fun solvePart2(): Any {
        numbers.forEachIndexed { index, number ->
            val product = numbers.drop(index + 1).product(2020 - number)
            if (product != null) return product * number
        }
        return Unit
    }

    private val numbers = lines.map { it.toInt() }.sorted()

    private fun List<Int>.product(sum: Int) = find { v -> findLast { it + v == sum } != null }?.let { it * (sum - it) }
}
