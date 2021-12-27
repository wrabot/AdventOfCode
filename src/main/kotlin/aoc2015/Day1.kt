package aoc2015

import forEachInput
import tools.log

object Day1 {
    fun solve() = forEachInput(2015, 1, 1) { lines ->
        log("part 1: ")
        lines[0].fold(0) { acc, c -> if (c == '(') acc + 1 else acc - 1 }.log()
        log("part 2: ")
        lines[0].runningFold(0) { acc, c -> if (c == '(') acc + 1 else acc - 1 }.indexOfFirst { it < 0 }.log()
    }
}
