package aoc2024

import Day
import tools.text.toInts

class Day17(test: Int? = null) : Day(test) {
    private val a = lines[0].split(": ")[1].toLong()
    private val program = lines[4].split(": ")[1].toInts(",")

    override fun solvePart1() = SpatialComputer(program, a).execute().joinToString(",")

    override fun solvePart2(): Long {
        /* reverse input code
        2,4, 1,2, 7,5, 1,7, 4,4, 0,3, 5,5, 3,0

        do {
            b = a and 7
            b = b xor 2
            c = a / (1 shl b)
            b = b xor 7
            b = b xor c
            a = a / 8
            out b and 7
        } while(a != 0)
        */
        val result = find(0L, program.lastIndex) ?: 0
        return if (SpatialComputer(program, result).execute() == program) result else 0
    }

    fun find(result: Long, index: Int): Long? {
        if (index < 0) return result
        val choices = (0..7).filter {
            val a = result * 8 + it
            var b = it xor 2
            val c = ((a shr  b) and 7).toInt()
            b = b xor 7
            b = b xor c
            program[index] == b and 7
        }
        return if (choices.isEmpty()) null else choices.firstNotNullOfOrNull {
            find(result * 8 + it, index - 1)
        }
    }

    private data class SpatialComputer(val program: List<Int>, var a: Long) {
        private val out = mutableListOf<Int>()
        private var b = 0
        private var c = 0
        private var pointer = 0

        fun execute(): List<Int> {
            while (pointer < program.size) {
                when (program[pointer++]) {
                    0 -> a = a shr comboOperand()
                    1 -> b = b xor program[pointer++]
                    2 -> b = comboOperand() and 7
                    3 -> if (a != 0L) pointer = program[pointer] else pointer += 2
                    4 -> {
                        b = b xor c
                        pointer++
                    }

                    5 -> out.add(comboOperand() and 7)
                    6 -> b = (a shr comboOperand()).toInt()
                    7 -> c = (a shr comboOperand()).toInt()
                }
            }
            return out
        }

        private fun comboOperand() = when (val l = program[pointer++]) {
            4 -> a.toInt()
            5 -> b
            6 -> c
            7 -> error("combo")
            else -> l
        }
    }
}
