package aoc2015

import Day
import tools.sequence.combinations

class Day9(test: Int? = null) : Day(test) {
    override fun solvePart1() = lengths.minOrNull()!!

    override fun solvePart2() = lengths.maxOrNull()!!

    private val distances = mutableMapOf<Pair<String, String>, Int>()
    private val cities = mutableSetOf<String>()
    private val lengths: List<Int>

    init {
        lines.map { it.split(" to ", " = ") }.forEach { (a, b, d) ->
            cities.add(a)
            cities.add(b)
            d.toInt().run {
                distances[a to b] = this
                distances[b to a] = this
            }
        }
        lengths = cities.toList().combinations().map {
            it.zipWithNext { a, b -> distances[a to b]!! }.sum()
        }.toList()
    }
}
