package aoc2020

import Day

class Day7 : Day() {
    override fun solvePart1() = rules.keys.filter { contains(it, "shiny gold") }.distinct().size
    override fun solvePart2() = count("shiny gold")

    private val rules = lines.map { it.split(" bags contain ") }.associate { (a, b) ->
        a to if (b.startsWith("no")) emptyList() else b.split(", ").map {
            it.split(" ").let { (n, u, v) -> n.toInt() to "$u $v" }
        }
    }

    private fun contains(container: String, name: String): Boolean =
       rules.getValue(container).any { it.second == name || contains(it.second, name) }

    private fun count(container: String): Int = rules.getValue(container).sumOf { it.first * (1 + count(it.second)) }
}
