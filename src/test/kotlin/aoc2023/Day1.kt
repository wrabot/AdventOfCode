package aoc2023

import Day

class Day1 : Day() {
    override fun solvePart1() = lines.sumOf { it.calibrationPart1().toInt() }
    private fun String.calibrationPart1() = first { it.isDigit() }.toString() + last { it.isDigit() }

    override fun solvePart2() = lines.sumOf { it.calibrationPart2().toInt() }
    private fun String.calibrationPart2() =
        findAnyOf(allFigures)!!.second.figure() + findLastAnyOf(allFigures)!!.second.figure()

    private fun String.figure() = figures[this] ?: this

    private val figures = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )

    private val allFigures = figures.keys + figures.values
}
