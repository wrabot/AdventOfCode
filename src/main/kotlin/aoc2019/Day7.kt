package aoc2019

import tools.Day
import tools.range.combinations

class Day7(test: Int? = null) : Day(2019, 7, test) {
    override fun solvePart1() = (0..4).toList().combinations().maxOf { phases ->
        val runtimes = List(5) { Runtime(code).apply { execute(phases[it]) } }
        runtimes.fold(0) { input, runtime -> runtime.execute(input)!! }
    }

    override fun solvePart2() = (5..9).toList().combinations().maxOf { phases ->
        val runtimes = List(5) { Runtime(code).apply { execute(phases[it]) } }
        var current: Int? = 0
        while (true) {
            current = runtimes.fold(current) { input, runtime -> runtime.execute(input) } ?: break
        }
        current ?: 0
    }

    private val code = lines.first().split(",").map { it.toInt() }

    class Runtime(code: List<Int>) {
        private val memory = code.toMutableList()
        private var offset: Int = 0

        fun execute(input: Int?): Int? {
            var i = input
            while(true) {
                if (offset >= memory.size) error("no opcode")
                val opcode = read()
                val firstMode = opcode / 100 % 10
                val secondMode = opcode / 1000 % 10
                when (opcode % 100) {
                    1 -> read3().run { memory[third] = first.toValue(firstMode) + second.toValue(secondMode) }
                    2 -> read3().run { memory[third] = first.toValue(firstMode) * second.toValue(secondMode) }
                    3 -> with (i ?: break) {
                        memory[read()] = this
                        i = null
                    }

                    4 -> return read().toValue(firstMode)
                    5 -> read2().run { if (first.toValue(firstMode) != 0) offset = second.toValue(secondMode) }
                    6 -> read2().run { if (first.toValue(firstMode) == 0) offset = second.toValue(secondMode) }
                    7 -> read3().run {
                        memory[third] = if (first.toValue(firstMode) < second.toValue(secondMode)) 1 else 0
                    }

                    8 -> read3().run {
                        memory[third] = if (first.toValue(firstMode) == second.toValue(secondMode)) 1 else 0
                    }

                    99 -> break
                    else -> error("invalid opcode $opcode")
                }
            }
            offset-- // stay on instruction
            return null
        }

        private fun read() = memory[offset++]
        private fun read2() = Pair(read(), read())
        private fun read3() = Triple(read(), read(), read())
        private fun Int.toValue(mode: Int) = if (mode == 1) this else memory[this]
    }
}
