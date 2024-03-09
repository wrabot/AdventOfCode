package aoc2016

import Day

class Day25(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        Computer(input).apply { registers["a"] = 0 }.run()
        // after code analysing, out pattern is a+7*362 in binary and reserved
        // a=0 => 2534 => 011001111001
        // so the lowest pattern is 010101010101
        return "010101010101".reversed().toInt(2) - 2534
    }

    override fun solvePart2() = Unit
}
