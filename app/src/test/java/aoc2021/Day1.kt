package aoc2021

import forEachInput
import log

object Day1 {
    fun solve() = forEachInput(2021, 1, 1, 2) { lines ->
        val depths = lines.map { it.toInt() }
        log("part 1: ")
        depths.zipWithNext().count { it.first < it.second }.log()
        log("part 2: ")
        depths.windowed(3).map { it.sum() }.zipWithNext().count { it.first < it.second }.log()
    }
}
