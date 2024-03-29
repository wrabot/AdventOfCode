package aoc2019

import Day

class Day5 : Day() {
    override fun solvePart1() = Runtime(code.toMutableList(), 1).execute()

    override fun solvePart2() = Runtime(code.toMutableList(), 5).execute()

    private val code = lines.first().split(",").map { it.toInt() }

    private data class Runtime(val memory: MutableList<Int>, val input: Int) {
        private var offset = 0

        fun execute(): Int {
            var output = 0
            while (offset < memory.size) {
                val opcode = read()
                val firstMode = opcode / 100 % 10
                val secondMode = opcode / 1000 % 10
                when (opcode % 100) {
                    1 -> read3().run { memory[third] = first.toValue(firstMode) + second.toValue(secondMode) }
                    2 -> read3().run { memory[third] = first.toValue(firstMode) * second.toValue(secondMode) }
                    3 -> memory[read()] = input
                    4 -> output = read().toValue(firstMode)
                    // part 2 start
                    5 -> read2().run { if (first.toValue(firstMode) != 0) offset = second.toValue(secondMode) }
                    6 -> read2().run { if (first.toValue(firstMode) == 0) offset = second.toValue(secondMode) }
                    7 -> read3().run {
                        memory[third] = if (first.toValue(firstMode) < second.toValue(secondMode)) 1 else 0
                    }

                    8 -> read3().run {
                        memory[third] = if (first.toValue(firstMode) == second.toValue(secondMode)) 1 else 0
                    }
                    // part 2 end
                    99 -> break
                    else -> error("invalid opcode $opcode")
                }
            }
            return output
        }

        private fun read() = memory[offset++]
        private fun read2() = Pair(read(), read())
        private fun read3() = Triple(read(), read(), read())
        private fun Int.toValue(mode: Int) = if (mode == 1) this else memory[this]
    }
}
