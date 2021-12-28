package aoc2020

import tools.Day

class Day8 : Day(2020, 8) {
    override fun getPart1() = code.run().second

    override fun getPart2(): Any {
        for (fixIndex in code.indices) {
            val original = code[fixIndex].op
            val fixOp = when (original) {
                "nop" -> "jmp"
                "jmp" -> "nop"
                else -> null
            }
            if (fixOp != null) {
                code[fixIndex].op = fixOp
                val result = code.run()
                if (result.first == code.size) return result.second
                code[fixIndex].op = original
            }
        }
        error("not found")
    }


    private val code = lines.map { it.split(" ") }.map { (op, arg) -> Ins(op, arg.toInt()) }

    data class Ins(var op: String, val arg: Int, var used: Boolean = false)

    private fun List<Ins>.run(): Pair<Int, Int> {
        forEach { it.used = false }
        var acc = 0
        var index = 0
        while (index < size && !this[index].used) {
            this[index].used = true
            when (this[index].op) {
                "jmp" -> index += this[index].arg
                "acc" -> acc += this[index++].arg
                else -> index++
            }
        }
        return index to acc
    }
}
