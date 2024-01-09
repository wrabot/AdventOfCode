package aoc2015

import Day
import tools.combinations

class Day13(test: Int? = null) : Day(test) {
    override fun solvePart1() = persons.drop(1).toList().combinations().maxOf {
        (listOf(persons.first()) + it + persons.first()).happiness()
    }

    override fun solvePart2() = persons.toList().combinations().maxOf { it.happiness() }

    private val persons = mutableSetOf<String>()
    private val happiness = mutableMapOf<Pair<String, String>, Int>()

    private fun List<String>.happiness() = zipWithNext { a, b -> happiness[a to b]!! + happiness[b to a]!! }.sum()

    init {
        val regex = "(.+) would gain (.+) happiness units by sitting next to (.+).".toRegex()
        lines.forEach {
            val (a, gain, b) = regex.matchEntire(it.replace("lose ", "gain -"))!!.destructured
            persons.add(a)
            persons.add(b)
            happiness[a to b] = gain.toInt()
        }
     }
}
