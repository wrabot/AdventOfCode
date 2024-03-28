package aoc2017

import Day
import tools.toWords

class Day8(test: Int? = null) : Day(test) {
    override fun solvePart1() = registers.values.max()
    override fun solvePart2() = max

    private data class Instruction(
        val register: String,
        val value: Int,
        val testRegister: String,
        val testOperator: String,
        val testValue: Int
    )

    private val instructions = lines.map { line ->
        line.toWords().let {
            val sign = if (it[1] == "inc") 1 else -1
            Instruction(it[0], sign * it[2].toInt(), it[4], it[5], it[6].toInt())
        }
    }

    private val registers = mutableMapOf<String, Int>()
    private var max = 0

    init {
        for (i in instructions) {
            val testValue = registers.getOrDefault(i.testRegister, 0)
            if (when (i.testOperator) {
                    "==" -> testValue == i.testValue
                    "!=" -> testValue != i.testValue
                    "<" -> testValue < i.testValue
                    "<=" -> testValue <= i.testValue
                    ">" -> testValue > i.testValue
                    ">=" -> testValue >= i.testValue
                    else -> error("!!!")
                }
            ) registers[i.register] = registers.getOrDefault(i.register, 0) + i.value
            max = max.coerceAtLeast(registers.values.max())
        }
    }
}
