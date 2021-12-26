package aoc2021

import forEachInput
import log

object Day6 {
    fun solve() = forEachInput(2021, 6, 1, 2) { lines ->
        var generation = LongArray(9) { 0 }
        lines[0].split(",").map { generation[it.toInt()]++ }
        repeat(256) {
            generation = LongArray(9) { timer ->
                when (timer) {
                    8 -> generation[0]
                    6 -> generation[0] + generation[7]
                    else -> generation[timer + 1]
                }
            }
            if (it == 79) {
                log("part 1: ")
                generation.sum().log()
            }
        }
        log("part 2: ")
        generation.sum().log()
    }
}
