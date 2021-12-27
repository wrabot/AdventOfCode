package aoc2020

import forEachInput
import tools.log

object Day4 {
    fun solve() = forEachInput(2020, 4, 1) { lines ->
        val passports = mutableListOf(mutableMapOf<String, String>())
        lines.forEach { line ->
            if (line.isBlank()) {
                passports.add(mutableMapOf())
            } else {
                line.split(" ").map { it.split(":") }.forEach { (key, value) ->
                    passports.last()[key] = value
                }
            }
        }
        val mandatory = mapOf<String, (String) -> Boolean>(
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

        log("part 1: ")
        passports.count { (it.keys intersect mandatory.keys).size == 7 }.log()

        log("part 2: ")
        passports.count { passport ->
            mandatory.all { it.value(passport[it.key].orEmpty()) }
        }.log()
    }
}
