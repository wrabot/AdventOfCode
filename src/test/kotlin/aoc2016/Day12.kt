package aoc2016

import Day
import tools.toWords

class Day12(test: Int? = null) : Day(test) {
    override fun solvePart1() = Computer(code)()
    override fun solvePart2() = Computer(code, mutableMapOf("c" to 1))()

    data class Computer(
        val code: List<Instruction>,
        val registers: MutableMap<String, Int> = mutableMapOf(),
        var offset: Int = 0,
    ) {
        operator fun invoke(): Int {
            while (offset < code.size) {
                var d = 1
                when (val i = code[offset]) {
                    is CPY -> registers[i.y] = get(i.x)
                    is DEC -> registers[i.x] = get(i.x) - 1
                    is INC -> registers[i.x] = get(i.x) + 1
                    is JNZ -> if (get(i.x) != 0) d = i.y
                }
                offset += d
            }
            return registers["a"]!!
        }

        operator fun get(x: String) = x.toIntOrNull() ?: registers.getOrDefault(x, 0)
    }

    sealed interface Instruction
    data class CPY(val x: String, val y: String) : Instruction
    data class INC(val x: String) : Instruction
    data class DEC(val x: String) : Instruction
    data class JNZ(val x: String, val y: Int) : Instruction

    val code = lines.map {
        val words = it.toWords()
        when (words[0]) {
            "cpy" -> CPY(words[1], words[2])
            "inc" -> INC(words[1])
            "dec" -> DEC(words[1])
            "jnz" -> JNZ(words[1], words[2].toInt())
            else -> error("!!!")
        }
    }
}
