package aoc2018

import Day

class Day12(test: Int? = null) : Day(test) {
    override fun solvePart1() = solve(20)

    override fun solvePart2(): Long {
        // min et inc find by testing
        val min = 100
        val inc = 32
        val count = solve(min)
        return (50_000_000_000 - min) * inc + count
    }

    fun solve(repeat: Int): Int {
        var offset = 0
        var plants = initialState
        repeat(repeat) {
            offset += 2
            plants = "....$plants....".windowed(5).map { rules[it] ?: '.' }.joinToString("")
        }
        return plants.withIndex().filter { it.value == '#' }.sumOf { it.index - offset }
    }

    private val initialState = lines.first().removePrefix("initial state: ")
    private val rules = lines.drop(2).associate {
        val (a, b) = it.split(" => ")
        a to b
    }
}
