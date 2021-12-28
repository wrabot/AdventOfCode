package aoc2019

import tools.Day

class Day2 : Day(2019, 2) {
    override fun getPart1() = code.execute(12, 2)

    override fun getPart2() = (0..9999).first { code.execute(it / 100, it % 100) == 19690720 }

    private val code = lines.first().split(",").map { it.toInt() }

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
