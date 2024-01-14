package aoc2022

import Day

class Day10(test: Int? = null) : Day(test) {
    override fun solvePart1() = (20..220 step 40).sumOf { x[it - 1] * it }

    override fun solvePart2() = "\n" +
            (0..239).joinToString("") { if (it % 40 in x[it] - 1..x[it] + 1) "#" else "." }
                .chunked(40).joinToString("\n")

    private val x = lines.fold(listOf(1)) { list, line ->
        when (line) {
            "noop" -> list + list.last()
            else -> list + list.last() + (list.last() + line.removePrefix("addx ").toInt())
        }
    }
}
