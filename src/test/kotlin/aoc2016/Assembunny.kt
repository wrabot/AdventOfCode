package aoc2016

import tools.text.toWords

// Day 12 / 23 / 25
class Assembunny(input: String) {
    val registers: MutableMap<String, Int> = mutableMapOf()
    var offset: Int = 0

    fun run(): Int {
        var out = ""
        while (offset < code.size) {
            var d = 1
            when (val i = toggle(code[offset])) {
                is CPY -> registers[i.y] = get(i.x)
                is DEC -> registers[i.x] = get(i.x) - 1
                is INC -> registers[i.x] = get(i.x) + 1
                is JNZ -> if (get(i.x) != 0) d = get(i.y)
                // Day 23
                is TGL -> (offset + get(i.x)).let { toggle[it] = toggle.getOrDefault(it, 0) + 1 }
                is ADD -> registers[i.y] = get(i.y) + get(i.x)
                is MUL -> registers[i.y] = get(i.y) * get(i.x)
                // Day 25
                is OUT -> {
                    out += get(i.x)
                    if (out.length == 40) {
                        println(out)
                        break
                    }
                }
            }
            offset += d
        }
        return registers["a"]!!
    }

    // Day 23
    private val toggle = mutableMapOf<Int, Int>()
    private fun toggle(instruction: Instruction): Instruction {
        val t = ((toggle[offset] ?: return instruction) % 2) == 1
        return when (instruction) {
            is INC -> if (t) DEC(instruction.x) else instruction
            is DEC -> if (t) INC(instruction.x) else instruction
            is TGL -> if (t) INC(instruction.x) else DEC(instruction.x)
            is JNZ -> if (t) CPY(instruction.x, instruction.y) else instruction
            is CPY -> if (t) JNZ(instruction.x, instruction.y) else instruction
            else -> instruction
        }
    }

    private operator fun get(x: String) = x.toIntOrNull() ?: registers.getOrDefault(x, 0)

    private sealed interface Instruction
    private data class CPY(val x: String, val y: String) : Instruction
    private data class INC(val x: String) : Instruction
    private data class DEC(val x: String) : Instruction
    private data class JNZ(val x: String, val y: String) : Instruction
    private data class TGL(val x: String) : Instruction // Day 23
    private data class ADD(val x: String, val y: String) : Instruction // Day 23
    private data class MUL(val x: String, val y: String) : Instruction // Day 23
    private data class OUT(val x: String) : Instruction // Day 25

    private val code = input.lines().map {
        val words = it.toWords()
        when (words[0]) {
            "cpy" -> CPY(words[1], words[2])
            "inc" -> INC(words[1])
            "dec" -> DEC(words[1])
            "jnz" -> JNZ(words[1], words[2])
            "tgl" -> TGL(words[1])// Day 23
            "add" -> ADD(words[1], words[2])// Day 23
            "mul" -> MUL(words[1], words[2])// Day 23
            "out" -> OUT(words[1])// Day 25
            else -> error("!!!")
        }
    }
}
