package aoc2016

import Day

class Day12(test: Int? = null) : Day(test) {
    override fun solvePart1() = Assembunny(input).run()
    override fun solvePart2() = Assembunny(input).apply { registers["c"] = 1 }.run()
}
