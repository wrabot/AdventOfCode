package aoc2020

import forEachInput
import tools.log

object Day15 {
    fun solve() = forEachInput(2020, 15, 2) { lines ->
        lines.map { line -> line.split(",").map { it.toInt() } }.run {
            log("part 1: ")
            forEach { it.memory(2020).log() }
            log("part 2: ")
            forEach { it.memory(30000000).log() }
        }
    }

    private fun List<Int>.memory(turns: Int): Int {
        val numbers = IntArray(turns) { 0 }
        dropLast(1).forEachIndexed { index, n -> numbers[n] = index + 1 }
        var last = last()
        (size until turns).forEach {
            last = numbers[last].apply { numbers[last] = it }.run { if (this == 0) 0 else it - this }
        }
        return last
    }
}
