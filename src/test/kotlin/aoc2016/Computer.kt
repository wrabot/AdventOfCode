package aoc2016

import tools.toWords

class Computer(input: String) {
    val registers: MutableMap<String, Int> = mutableMapOf()
    var offset: Int = 0
    
    fun run(): Int {
        while (offset < code.size) {
            var d = 1
            when (val i = code[offset]) {
                is CPY -> registers[i.y] = get(i.x)
                is DEC -> registers[i.x] = get(i.x) - 1
                is INC -> registers[i.x] = get(i.x) + 1
                is JNZ -> if (get(i.x) != 0) d = get(i.y)
            }
            offset += d
        }
        return registers["a"]!!
    }

    private operator fun get(x: String) = x.toIntOrNull() ?: registers.getOrDefault(x, 0)
    
    private sealed interface Instruction
    private data class CPY(val x: String, val y: String) : Instruction
    private data class INC(val x: String) : Instruction
    private data class DEC(val x: String) : Instruction
    private data class JNZ(val x: String, val y: String) : Instruction

    private val code = input.lines().map {
        val words = it.toWords()
        when (words[0]) {
            "cpy" -> CPY(words[1], words[2])
            "inc" -> INC(words[1])
            "dec" -> DEC(words[1])
            "jnz" -> JNZ(words[1], words[2])
            else -> error("!!!")
        }
    }
}
