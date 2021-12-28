package aoc2020

import tools.Day

class Day7 : Day(2020, 7) {
    //TODO remove mutable ?
    override fun getPart1() = mutableSetOf<String>().apply {
        rules.keys.forEach { if (rules.contains(it, "shiny gold")) add(it) }
    }.count()

    override fun getPart2() = rules.count("shiny gold")

    private val rules = lines.map { it.split(" bags contain ") }.map { (a, b) ->
        a to if (b.startsWith("no")) emptyList() else b.split(", ").map {
            it.split(" ").let { (n, u, v) -> n.toInt() to "$u $v" }
        }
    }.toMap()

    private fun Map<String, List<Pair<Int, String>>>.contains(container: String, name: String): Boolean =
        getValue(container).any { it.second == name || contains(it.second, name) }

    private fun Map<String, List<Pair<Int, String>>>.count(container: String): Int =
        getValue(container).map { it.first * (1 + count(it.second)) }.sum()
}
