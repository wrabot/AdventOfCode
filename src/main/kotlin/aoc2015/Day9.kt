package aoc2015

import forEachInput
import tools.log

object Day9 {
    fun solve() = forEachInput(2015, 9, 2) { lines ->
        lines.map { it.split(" to ", " = ") }.forEach { (a, b, d) ->
            cities.add(a)
            cities.add(b)
            d.toInt().run {
                distances[listOf(a, b)] = this
                distances[listOf(b, a)] = this
            }
        }

        log("part 1: ")
        distances.log()

        log("part 2: ")
    }

    private val distances = mutableMapOf<List<String>, Int>()
    private val cities = mutableSetOf<String>()
}
