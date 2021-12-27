package aoc2020

import forEachInput
import tools.log
import tools.sum
import java.math.BigInteger

object Day18 {
    fun solve() = forEachInput(2020, 18, 2) { lines ->
        val list = lines.map { it.replace(" ", "") }
        log("part 1: ")
        list.map { Expr(it, false).eval() }.sum().log() //part1
        log("part 2: ")
        list.map { Expr(it, true).eval() }.sum().log() //part2
    }

    data class Expr(val text: String, val part2: Boolean) {
        fun eval(): BigInteger {
            val stack = mutableListOf(operand())
            while (index < text.length) {
                when (text[index++]) {
                    ')' -> break
                    '*' -> if (part2) stack.add(operand()) else stack[stack.lastIndex] *= operand()
                    '+' -> stack[stack.lastIndex] += operand()
                    else -> error("invalid operator")
                }
            }
            return stack.reduce { acc, bi -> acc * bi }
        }

        private var index: Int = 0
        private fun operand() = if (text[index++] == '(') eval() else text[index - 1].toString().toBigInteger()
    }
}
