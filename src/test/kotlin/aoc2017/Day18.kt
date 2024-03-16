package aoc2017

import Day
import tools.toWords

class Day18(test: Int? = null) : Day(test) {
    override fun solvePart1() = Duet1(code).run {
        run()
        sound
    }

    class Duet1(code: List<List<String>>) : Duet(code) {
        var sound = 0L
        override fun snd(value: Long) {
            sound = value
        }

        override fun rcv(value: Long) = if (value == 0L) 0L else null
    }

    override fun solvePart2(): Int {
        val a = Duet2(code, 0)
        val b = Duet2(code, 1)
        a.output = b.input
        b.output = a.input
        do {
            a.run()
            b.run()
        } while (a.input.isNotEmpty() || b.input.isNotEmpty())
        return b.count
    }

    class Duet2(code: List<List<String>>, id: Long) : Duet(code) {
        val input = mutableListOf<Long>()
        var output = mutableListOf<Long>()
        var count = 0
            private set

        override fun snd(value: Long) {
            output.add(value)
            count++
        }

        override fun rcv(value: Long) = input.removeFirstOrNull()

        init {
            registers["p"] = id
        }
    }

    abstract class Duet(val code: List<List<String>>) {
        private var offset = 0
        protected val registers = mutableMapOf<String, Long>()
        private fun valueOrRegister(key: String) = key.toLongOrNull() ?: getRegister(key)
        private fun getRegister(key: String) = registers.getOrDefault(key, 0)
        abstract fun snd(value: Long)
        abstract fun rcv(value: Long): Long?
        fun run() {
            while (true) {
                val i = code[offset]
                when (i[0]) {
                    "snd" -> snd(valueOrRegister(i[1]))
                    "set" -> registers[i[1]] = valueOrRegister(i[2])
                    "add" -> registers[i[1]] = getRegister(i[1]) + valueOrRegister(i[2])
                    "mul" -> registers[i[1]] = getRegister(i[1]) * valueOrRegister(i[2])
                    "mod" -> registers[i[1]] = getRegister(i[1]).mod(valueOrRegister(i[2]))
                    "rcv" -> registers[i[1]] = rcv(valueOrRegister(i[1])) ?: break
                    "jgz" -> if (valueOrRegister(i[1]) > 0L) offset += valueOrRegister(i[2]).toInt() - 1
                }
                offset++
            }
        }
    }

    private val code = lines.map { it.toWords() }
}
