package aoc2020

import forEachInput
import tools.log

object Day6 {
    fun solve() = forEachInput(2020, 6, 1) { lines ->
        val groups = mutableListOf(mutableListOf<String>())
        lines.forEach {
            if (it.isBlank()) {
                groups.add(mutableListOf())
            } else {
                groups.last().add(it)
            }
        }

        log("part 1: ")
        groups.map { it.reduce { acc, s -> acc + s }.toSet().count() }.sum().log()

        log("part 2: ")
        groups.map { g -> g.map { it.toSet() }.reduce { acc, s -> acc intersect s }.count() }.sum().log()
    }
}
