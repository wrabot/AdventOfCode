package aoc2017

import Day

class Day1(test: Int? = null) : Day(test) {
    override fun solvePart1() = solve(1)
    override fun solvePart2() = solve(input.length / 2)
    fun solve(offset: Int) =
        input.mapIndexed { index, c -> if (c == input[(index + offset) % input.length]) c - '0' else 0 }.sum()
}
