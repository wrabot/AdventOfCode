package aoc2017

import Day

class Day24(test: Int? = null) : Day(test) {
    override fun solvePart1() = strongest(components, 0, 0)

    private fun strongest(components: List<List<Int>>, current: Int, strength: Int): Int =
        components.filter { current in it }.maxOfOrNull {
            strongest(components - setOf(it), (it - current).single(), strength + it.sum())
        } ?: strength

    
    // strength < 10000
    override fun solvePart2() = longest(components, 0, 0, 0) % maxStrength

    private val maxStrength = 10000
    private fun longest(components: List<List<Int>>, current: Int, length: Int, strength: Int): Int =
        components.filter { current in it }.maxOfOrNull {
            longest(components - setOf(it), (it - current).single(), length + 1, strength + it.sum())
        } ?: (length * maxStrength + strength)

    private val components = lines.map { line -> line.split("/").map { it.toInt() } }
}
