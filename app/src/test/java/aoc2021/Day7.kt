package aoc2021

import forEachInput
import log
import kotlin.math.absoluteValue

object Day7 {
    fun solve() = forEachInput(2021, 7, 1, 2) { lines ->
        val positions = lines[0].split(",").map { it.toInt() }.groupingBy { it }.eachCount()
        val min = positions.minOf { it.key }
        val max = positions.maxOf { it.key }

        log("part 1: ")
        (min..max).minOf { target ->
            positions.entries.sumOf {
                (it.key - target).absoluteValue * it.value
            }
        }.log()

        log("part 2: ")
        (min..max).minOf { target ->
            positions.entries.sumOf {
                val n = (it.key - target).absoluteValue
                n * (n + 1) / 2 * it.value
            }
        }.log()
    }
}
