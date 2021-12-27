package aoc2020

import forEachInput
import tools.log

object Day5 {
    fun solve() = forEachInput(2020, 5, 1) { lines ->
        val ids = lines.map {
            it.replace('F', '0')
                .replace('B', '1')
                .replace('L', '0')
                .replace('R', '1')
                .toInt(2)
        }.sorted()

        log("part 1: ")
        ids.maxOrNull().log()

        log("part 2: ")
        (ids.filterIndexed { index, i -> index + ids.first() != i }.first() - 1).log()
    }
}
