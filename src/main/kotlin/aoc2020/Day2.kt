package aoc2020

import tools.Day

class Day2 : Day(2020, 2) {
    override fun getPart1() = rules.count { (min, max, letter, password) ->
        password.count { it.toString() == letter } in min.toInt()..max.toInt()
    }

    override fun getPart2() = rules.count { (first, second, letter, password) ->
        listOf(first, second).count { password[it.toInt() - 1].toString() == letter } == 1
    }

    private val rules = lines.map { it.split("-", " ", ": ") }
}
