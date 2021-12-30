package aoc2020

import tools.Day

class Day4 : Day(2020, 4) {
    override fun solvePart1() = passports.count { (it.keys intersect mandatory.keys).size == 7 }

    override fun solvePart2() = passports.count { passport ->
        mandatory.all { it.value(passport[it.key].orEmpty()) }
    }

    //TODO remove mutable ?
    private val passports = mutableListOf(mutableMapOf<String, String>()).apply {
        lines.forEach { line ->
            if (line.isBlank()) {
                add(mutableMapOf())
            } else {
                line.split(" ").map { it.split(":") }.forEach { (key, value) ->
                    last()[key] = value
                }
            }
        }
    }

    private val mandatory = mapOf<String, (String) -> Boolean>(
        "byr" to { (it.toIntOrNull() ?: 0) in 1920..2002 },
        "iyr" to { (it.toIntOrNull() ?: 0) in 2010..2020 },
        "eyr" to { (it.toIntOrNull() ?: 0) in 2020..2030 },
        "hgt" to {
            when {
                it.endsWith("cm") -> it.removeSuffix("cm").toInt() in 150..193
                it.endsWith("in") -> it.removeSuffix("in").toInt() in 59..76
                else -> false
            }
        },
        "hcl" to { it.matches("#[0-9a-f]{6}".toRegex()) },
        "ecl" to { it in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") },
        "pid" to { it.matches("[0-9]{9}".toRegex()) }
    )
}
