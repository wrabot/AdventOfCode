package aoc2015

import tools.Day

class Day9(test: Int? = null) : Day(2015, 9, test) {
    override fun solvePart1() = Unit

    override fun solvePart2() = Unit

    private val distances = mutableMapOf<List<String>, Int>()
    private val cities = mutableSetOf<String>()

    init {
        lines.map { it.split(" to ", " = ") }.forEach { (a, b, d) ->
            cities.add(a)
            cities.add(b)
            d.toInt().run {
                distances[listOf(a, b)] = this
                distances[listOf(b, a)] = this
            }
        }
    }
}
