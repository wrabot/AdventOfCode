package aoc2020

import Day

class Day9 : Day() {
    override fun solvePart1(): Any {
        for (i in 25 until numbers.size) {
            val number = numbers[i]
            if ((1..24).all { first -> ((first + 1)..25).all { second -> number != numbers[i - first] + numbers[i - second] } }) {
                invalid = number
                return invalid
            }
        }
        error("not found")
    }

    override fun solvePart2(): Any {
        part1 // force part1
        var start = 0
        var end = 0
        var sum = 0L
        while (end < numbers.size) {
            if (sum == invalid) return numbers.subList(start, end).run { minOrNull()!! + maxOrNull()!! }
            sum += if (sum > invalid) -numbers[start++] else numbers[end++]
        }
        error("not found")
    }

    private val numbers = lines.map { it.toLong() }
    private var invalid = 0L
}
