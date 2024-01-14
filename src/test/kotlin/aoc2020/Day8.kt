package aoc2020

import Day

class Day8 : Day() {
    override fun solvePart1() = execute().run { acc }

    override fun solvePart2(): Any {
        for (fixIndex in code.indices) {
            val original = code[fixIndex].op
            val fixOp = when (original) {
                "nop" -> "jmp"
                "jmp" -> "nop"
                else -> null
            }
            if (fixOp != null) {
                code[fixIndex].op = fixOp
                if (execute()) return acc
                code[fixIndex].op = original
            }
        }
        error("not found")
    }

    data class Ins(var op: String, val arg: Int, var used: Boolean = false)

    private val code = lines.map { it.split(" ") }.map { (op, arg) -> Ins(op, arg.toInt()) }

    var acc = 0

    private fun execute(): Boolean {
        acc = 0
        code.forEach { it.used = false }
        var index = 0
        while (index < code.size) {
            if (code[index].used) return false
            code[index].used = true
            when (code[index].op) {
                "jmp" -> index += code[index].arg
                "acc" -> acc += code[index++].arg
                else -> index++
            }
        }
        return true
    }
}
