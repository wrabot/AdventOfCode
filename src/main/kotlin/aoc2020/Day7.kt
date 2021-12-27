package aoc2020

import forEachInput
import tools.log

object Day7 {
    fun solve() = forEachInput(2020, 7, 1) { lines ->
        log("part 1: ")
        val rules = lines.map { it.split(" bags contain ") }.map { (a, b) ->
            a to if (b.startsWith("no")) emptyList() else b.split(", ").map {
                it.split(" ").let { (n, u, v) -> n.toInt() to "$u $v" }
            }
        }.toMap()

        //part1
        val bags = mutableSetOf<String>()
        rules.keys.forEach {
            if (rules.contains(it, "shiny gold")) bags.add(it)
        }
        bags.count().log()

        log("part 2: ")
        rules.count("shiny gold").log()
    }

    private fun Map<String, List<Pair<Int, String>>>.contains(container: String, name: String): Boolean =
        getValue(container).any { it.second == name || contains(it.second, name) }

    private fun Map<String, List<Pair<Int, String>>>.count(container: String): Int =
        getValue(container).map { it.first * (1 + count(it.second)) }.sum()
}
