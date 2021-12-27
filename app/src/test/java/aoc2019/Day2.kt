package aoc2019

import forEachInput
import tools.log

object Day2 {
    fun solve() = forEachInput(2019, 2, 1) { lines ->
        val code = lines.first().split(",").map { it.toInt() }

        log("part 1: ")
        code.execute(12, 2).log()

        log("part 2: ")
        (0..9999).find { code.execute(it / 100, it % 100) == 19690720 }.log()
    }

    private fun List<Int>.execute(noun: Int, verb: Int): Int {
        val code = toMutableList()
        code[1] = noun
        code[2] = verb
        for (i in code.indices step 4) {
            when (code[i]) {
                1 -> code[code[i + 3]] = code[code[i + 1]] + code[code[i + 2]]
                2 -> code[code[i + 3]] = code[code[i + 1]] * code[code[i + 2]]
                99 -> break
                else -> error("invalid op")
            }
        }
        return code.first()
    }
}
