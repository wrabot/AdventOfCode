package aoc2015

import Day

class Day1 : Day() {
    override fun solvePart1() = lines[0].sumOf { if (it == '(') 1L else -1 }

    override fun solvePart2() =
        lines[0].runningFold(0) { acc, c -> if (c == '(') acc + 1 else acc - 1 }.indexOfFirst { it < 0 }
}
