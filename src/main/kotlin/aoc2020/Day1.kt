package aoc2020

import forEachInput
import tools.log

object Day1 {
    fun solve() = forEachInput(2020, 1, 1) { lines ->
        val numbers = lines.map { it.toInt() }.sorted()

        log("part 1: ")
        numbers.product(2020)!!.log()

        log("part 2: ")
        numbers.mapIndexedNotNull { index: Int, i: Int ->
            numbers.drop(index + 1).product(2020 - i)?.let { it * i }
        }.first().log()
    }

    private fun List<Int>.product(sum: Int) = find { v -> findLast { it + v == sum } != null }?.let { it * (sum - it) }
}
