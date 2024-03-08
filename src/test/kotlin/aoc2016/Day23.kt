package aoc2016

import Day

class Day23(test: Int? = null) : Day(test) {
    // replace
    // inc a
    // dec c
    // jnz c -2
    // dec d
    // jnz d -5
    // by
    // mul (a+=c*d)
    // add c d
    // cpy 0 c
    // cpy 0 d
    // jnz 0 0 (nop)
    override fun solvePart1() = Computer(input).apply { registers["a"] = 7 }.run()
    override fun solvePart2() = Computer(input).apply { registers["a"] = 12 }.run()
}
