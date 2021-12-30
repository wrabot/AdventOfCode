package aoc2015

import tools.Day

class Day7 : Day(2015, 7) {
    override fun solvePart1() = operand("a")

    override fun solvePart2(): Any {
        val a = operand("a")
        elements.forEach { it.value.cache = null }
        elements.getValue("b").cache = a
        return operand("a")
    }

    data class Element(val eval: () -> Int) {
        var cache: Int? = null
        val value get() = cache ?: eval().apply { cache = this and 0xFFFF }
    }

    private val elements = mutableMapOf<String, Element>()

    private fun operand(input: String) = input.toIntOrNull() ?: elements.getValue(input).value

    init {
        lines.map { it.split(" -> ") }.forEach { (input, output) ->
            elements[output] = input.split(" ").let { operation ->
                Element(when (operation.size) {
                    1 -> { -> operand(operation[0]) } // 123
                    2 -> { -> operand(operation[1]).inv() } // not a -> b
                    3 -> when (operation[1]) {
                        "AND" -> { -> (operand(operation[0]) and operand(operation[2])) }
                        "OR" -> { -> (operand(operation[0]) or operand(operation[2])) }
                        "LSHIFT" -> { -> (operand(operation[0]) shl operand(operation[2])) }
                        "RSHIFT" -> { -> (operand(operation[0]) shr operand(operation[2])) }
                        else -> error("invalid operation")
                    }
                    else -> error("invalid size")
                })
            }
        }
    }
}
