package aoc2020

import Day

class Day5 : Day() {
    override fun solvePart1() = ids.maxOrNull()!!

    override fun solvePart2() = ids.filterIndexed { index, i -> index + ids.first() != i }.first() - 1

    private val ids = lines.map {
        it.replace('F', '0')
            .replace('B', '1')
            .replace('L', '0')
            .replace('R', '1')
            .toInt(2)
    }.sorted()
}
