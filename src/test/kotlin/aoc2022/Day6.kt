package aoc2022

import Day

class Day6(test: Int? = null) : Day(test) {
    override fun solvePart1() = lines.map { it.findMarker(4) }
    override fun solvePart2() = lines.map { it.findMarker(14) }
    private fun String.findMarker(size: Int) =
        toList().windowed(size).indexOfFirst { it.distinct().count() == size } + size
}
