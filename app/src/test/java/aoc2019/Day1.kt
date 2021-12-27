package aoc2019

import forEachInput
import tools.log

object Day1 {
    fun solve() = forEachInput(2019, 1, 2) { lines ->
        log("part 1: ")
        lines.map { it.toInt() / 3 - 2 }.sum().log()
        log("part 2: ")
        lines.map { line -> generateSequence(line.toInt()) { (it / 3 - 2).takeIf { it > 0 } }.drop(1).sum() }.sum().log()
    }
}
