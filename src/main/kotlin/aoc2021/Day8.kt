package aoc2021

import forEachInput
import tools.log

object Day8 {
    fun solve() = forEachInput(2021, 8, 1, 2) { lines ->
        val entries = lines.map { line ->
            val (input, output) = line.split(" | ")
            input.split(" ").map { it.toList().sorted() } to
                    output.split(" ").map { it.toList().sorted() }
        }

        log("part 1: ")
        entries.sumOf { entry -> entry.second.count { it.size in listOf(2, 3, 4, 7) } }.log()

        log("part 2: ")
        entries.sumOf { entry ->
            val digits = mutableMapOf<Int, List<Char>>()
            digits[1] = entry.first.first { it.size == 2 }
            digits[7] = entry.first.first { it.size == 3 }
            digits[4] = entry.first.first { it.size == 4 }
            digits[8] = entry.first.first { it.size == 7 }
            digits[9] = entry.first.first { it.size == 6 && it.containsAll(digits[4]!!) }
            digits[0] = entry.first.first { it.size == 6 && it != digits[9] && it.containsAll(digits[1]!!) }
            digits[6] = entry.first.first { it.size == 6 && it != digits[9] && it != digits[0] }
            digits[5] = entry.first.first { it.size == 5 && digits[6]!!.containsAll(it) }
            digits[3] = entry.first.first { it.size == 5 && it != digits[5] && digits[9]!!.containsAll(it) }
            digits[2] = entry.first.first { it.size == 5 && it != digits[5] && it != digits[3] }
            val dictionary = digits.map { it.value to it.key }.toMap()
            entry.second.map { dictionary[it] }.joinToString("").toInt()
        }.log()

    }
}
