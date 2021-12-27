package aoc2020

import forEachInput
import tools.log

object Day2 {
    fun solve() = forEachInput(2020, 2, 1) { lines ->
        val rules = lines.map { it.split("-", " ", ": ") }

        log("part 1: ")
        rules.count { (min, max, letter, password) ->
            password.count { it.toString() == letter } in min.toInt()..max.toInt()
        }.log()

        log("part 2: ")
        rules.count { (first, second, letter, password) ->
            listOf(first, second).count { password[it.toInt() - 1].toString() == letter } == 1
        }.log()
    }
}
