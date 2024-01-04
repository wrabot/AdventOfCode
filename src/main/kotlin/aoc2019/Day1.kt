package aoc2019

import Day

class Day1 : Day(2019, 1) {
    override fun solvePart1() = lines.sumOf { it.toInt() / 3 - 2 }

    override fun solvePart2() =
        lines.sumOf { line ->
            generateSequence(line.toInt()) { mass ->
                (mass / 3 - 2).takeIf { it > 0 }
            }.drop(1).sum()
        }
}
