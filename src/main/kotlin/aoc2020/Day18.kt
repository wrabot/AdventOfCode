package aoc2020

import tools.Day
import tools.sum
import java.math.BigInteger

class Day18 : Day(2020, 18) {
    override fun getPart1() = list.map { Expr(it, false).eval() }.sum()
    override fun getPart2() = list.map { Expr(it, true).eval() }.sum()

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

    private val list = lines.map { it.replace(" ", "") }
}
