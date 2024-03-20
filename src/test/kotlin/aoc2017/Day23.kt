package aoc2017

import Day
import tools.toWords
import kotlin.math.sqrt

class Day23(test: Int? = null) : Day(test) {
    override fun solvePart1() = Tablet(code).let {
        it.run()
        it.mulCount
    }

    // after analysing the program count number which is not prime in 106500..123500 step 17 
    override fun solvePart2() = (106500..123500 step 17).count { n ->
        (2..sqrt(n.toDouble()).toInt()).any { n % it == 0 }
    }

    class Tablet(val code: List<List<String>>) {
        var mulCount = 0
            private set
        val registers = mutableMapOf<String, Long>()
        private var offset = 0
        private fun valueOrRegister(key: String) = key.toLongOrNull() ?: getRegister(key)
        private fun getRegister(key: String) = registers.getOrDefault(key, 0)
        fun run() {
            while (offset < code.size) {
                val i = code[offset++]
                when (i[0]) {
                    "set" -> registers[i[1]] = valueOrRegister(i[2])
                    "sub" -> registers[i[1]] = getRegister(i[1]) - valueOrRegister(i[2])
                    "mul" -> {
                        mulCount++
                        registers[i[1]] = getRegister(i[1]) * valueOrRegister(i[2])
                    }

                    "jnz" -> if (valueOrRegister(i[1]) != 0L) offset += valueOrRegister(i[2]).toInt() - 1
                }
            }
        }
    }

    private val code = lines.map { it.toWords() }
}
