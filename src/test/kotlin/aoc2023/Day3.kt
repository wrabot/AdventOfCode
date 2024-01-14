package aoc2023

import Day

class Day3(test: Int? = null) : Day(test) {
    private val height = lines.count()
    private val width = lines[0].count()
    private val yIndices = 0 until height
    private val xIndices = 0 until width
    private val numberRegex = Regex("\\d+")

    override fun solvePart1() = lines
        .mapIndexed { y, line ->
            numberRegex.findAll(line).sumOf { if (isNextToSymbol(it.range, y)) it.value.toInt() else 0 }
        }
        .sum()

    private fun isNextToSymbol(x: IntRange, y: Int) =
        (y - 1..y + 1).intersect(yIndices).any { r ->
            (x.first - 1..x.last + 1).intersect(xIndices).any { c ->
                lines[r][c].let { !it.isDigit() && it != '.' }
            }
        }

    override fun solvePart2() = lines
        .flatMapIndexed { y, line ->
            numberRegex.findAll(line).flatMap { result ->
                getGears(result.range, y).map { gear ->
                    gear to result.value.toInt()
                }
            }
        }
        .groupBy({ it.first }, { it.second })
        .map { if (it.value.count() == 2) it.value.first() * it.value.last() else 0 }
        .sum()

    private fun getGears(x: IntRange, y: Int) =
        (y - 1..y + 1).intersect(yIndices).flatMap { r ->
            (x.first - 1..x.last + 1).intersect(xIndices).mapNotNull { c ->
                if (lines[r][c] == '*') r to c else null
            }
        }
}
