package aoc2015

import forEachInput
import tools.log

object Day7 {
    fun solve() = forEachInput(2015, 7, 1) { lines ->
        val circuit = Circuit()
        lines.map { it.split(" -> ") }.forEach { (input, output) ->
            circuit.elements[output] = input.split(" ").let { operation ->
                Circuit.Element(when (operation.size) {
                    1 -> { -> circuit.operand(operation[0]) } // 123
                    2 -> { -> circuit.operand(operation[1]).inv() } // not a -> b
                    3 -> when (operation[1]) {
                        "AND" -> { -> (circuit.operand(operation[0]) and circuit.operand(operation[2])) }
                        "OR" -> { -> (circuit.operand(operation[0]) or circuit.operand(operation[2])) }
                        "LSHIFT" -> { -> (circuit.operand(operation[0]) shl circuit.operand(operation[2])) }
                        "RSHIFT" -> { -> (circuit.operand(operation[0]) shr circuit.operand(operation[2])) }
                        else -> error("invalid operation")
                    }
                    else -> error("invalid size")
                })
            }
        }
        
        log("part 1: ")
        val a = circuit.operand("a").log()
        circuit.elements.forEach { it.value.cache = null }
        circuit.elements.getValue("b").cache = a
        
        log("part 2: ")
        circuit.operand("a").log()
    }


    class Circuit {
        data class Element(val eval: () -> Int) {
            var cache: Int? = null
            val value get() = cache ?: eval().apply { cache = this and 0xFFFF }
        }

        val elements = mutableMapOf<String, Element>()
        fun operand(input: String) = input.toIntOrNull() ?: elements.getValue(input).value
    }

}
