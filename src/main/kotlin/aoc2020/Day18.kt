package aoc2020

import Day

class Day18 : Day(2020, 18) {
    override fun solvePart1() = list.sumOf { Expr(it, false).eval() }
    override fun solvePart2() = list.sumOf { Expr(it, true).eval() }

    data class Expr(val text: String, val part2: Boolean) {
        fun eval(): Long {
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
        private fun operand() = if (text[index++] == '(') eval() else text[index - 1].toString().toLong()
    }

    private val list = lines.map { it.replace(" ", "") }
}
