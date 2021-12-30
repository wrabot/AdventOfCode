package aoc2015

import tools.Day

class Day1 : Day(2015, 1) {
    override fun solvePart1() =
        lines[0].fold(0) { acc, c -> if (c == '(') acc + 1 else acc - 1 }

    override fun solvePart2() =
        lines[0].runningFold(0) { acc, c -> if (c == '(') acc + 1 else acc - 1 }.indexOfFirst { it < 0 }
}
