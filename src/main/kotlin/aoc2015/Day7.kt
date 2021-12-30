package aoc2015

import tools.Day

@Suppress("SpellCheckingInspection")
class Day7 : Day(2015, 7) {
    override fun solvePart1() = eval("a")

    override fun solvePart2(): Any {
        val a = eval("a")
        outputs.clear()
        outputs["b"] = a
        return eval("a")
    }

    private val circuit = lines.associate {
        val (expression, output) = it.split(" -> ")
        output to expression.split(" ")
    }

    private val outputs = mutableMapOf<String, Int>()

    private fun eval(value: String): Int = value.toIntOrNull() ?: outputs.getOrPut(value) {
        circuit[value]!!.let { expression ->
            when (expression.size) {
                1 -> eval(expression[0])
                2 -> eval(expression[1]).inv() // not
                3 -> when (expression[1]) {
                    "AND" -> eval(expression[0]) and eval(expression[2])
                    "OR" -> eval(expression[0]) or eval(expression[2])
                    "LSHIFT" -> eval(expression[0]) shl expression[2].toInt()
                    "RSHIFT" -> eval(expression[0]) shr expression[2].toInt()
                    else -> error("invalid operation")
                }
                else -> error("invalid size")
            }
        }
    }
}
