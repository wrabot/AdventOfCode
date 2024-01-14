package aoc2022

import Day

class Day1(test: Int? = null) : Day(test) {
    override fun solvePart1() = calories.maxOf { it.sum() }

    override fun solvePart2() = calories.map { it.sum() }.sortedDescending().take(3).sum()

    private val calories = input.split("\n\n").map { elf ->
        elf.split("\n").map { it.toInt() }
    }
}
