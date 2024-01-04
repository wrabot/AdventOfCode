package aoc2019

import Day

class Day9(test: Int? = null) : Day(2019, 9, test) {
    override fun solvePart1() = Runtime(code).run { generateSequence { execute(1) } }.toList()

    override fun solvePart2() = Runtime(code).execute(2)!!

    private val code = lines.first().split(",").map { it.toLong() }

    class Runtime(code: List<Long>) {
        private var offset: Long = 0
        private var relative: Long = 0

        fun execute(input: Long?): Long? {
            var i = input
            while (true) {
                val opcode = read().toInt()
                val firstMode = opcode / 100 % 10
                val secondMode = opcode / 1000 % 10
                val thirdMode = opcode / 10000 % 10
                when (opcode % 100) {
                    1 -> read3().run {
                        set(
                            third.toAddress(thirdMode),
                            first.toValue(firstMode) + second.toValue(secondMode)
                        )
                    }

                    2 -> read3().run {
                        set(
                            third.toAddress(thirdMode),
                            first.toValue(firstMode) * second.toValue(secondMode)
                        )
                    }

                    3 -> with(i ?: break) {
                        set(read().toAddress(firstMode), this)
                        i = null
                    }

                    4 -> return read().toValue(firstMode)
                    5 -> read2().run { if (first.toValue(firstMode) != 0L) offset = second.toValue(secondMode) }
                    6 -> read2().run { if (first.toValue(firstMode) == 0L) offset = second.toValue(secondMode) }
                    7 -> read3().run {
                        set(
                            third.toAddress(thirdMode),
                            if (first.toValue(firstMode) < second.toValue(secondMode)) 1 else 0
                        )
                    }

                    8 -> read3().run {
                        set(
                            third.toAddress(thirdMode),
                            if (first.toValue(firstMode) == second.toValue(secondMode)) 1 else 0
                        )
                    }

                    9 -> relative += read().toValue(firstMode)

                    99 -> break
                    else -> error("invalid opcode $opcode")
                }
            }
            offset-- // stay on instruction
            return null
        }

        private fun read() = get(offset++)
        private fun read2() = Pair(read(), read())
        private fun read3() = Triple(read(), read(), read())
        private fun Long.toValue(mode: Int) = when (mode) {
            0 -> get(this)
            1 -> this
            2 -> get(relative + this)
            else -> error("invalid mode $mode")
        }

        private fun Long.toAddress(mode: Int) = when (mode) {
            0 -> this
            2 -> relative + this
            else -> error("invalid mode $mode")
        }

        private var memory = mutableMapOf<Long, Long>().apply {
            code.forEachIndexed { index, value -> set(index.toLong(), value) }
        }

        private fun get(index: Long) = memory.getOrDefault(index, 0)
        private fun set(index: Long, value: Long) {
            memory[index] = value
        }
    }
}
