package aoc2023

import Day

class Day20(test: Int? = null) : Day(test) {
    override fun solvePart1(): Long {
        var high = 0L
        var low = 0L
        clear()
        val todo = mutableListOf<Pulse>()
        repeat(1000) {
            todo.addAll(broadcaster)
            low += broadcaster.size + 1
            while (true) {
                val pulse = todo.removeFirstOrNull() ?: break
                val module = modules[pulse.destination] ?: continue
                todo.addAll(module(pulse).apply {
                    low += count { !it.value }
                    high += count { it.value }
                })
            }
        }
        return high * low
    }

    // list is found by analyzing input
    override fun solvePart2() = listOf("xc", "th", "pd", "bp").fold(1L) { acc, s -> acc * findFirstLow(s) }

    private fun findFirstLow(destination: String): Int {
        clear()
        val todo = mutableListOf<Pulse>()
        var index = 0
        while (true) {
            index++
            todo.addAll(broadcaster)
            while (true) {
                val pulse = todo.removeFirstOrNull() ?: break
                val module = modules[pulse.destination] ?: continue
                todo.addAll(module(pulse).apply {
                    if (any { it.destination == destination && !it.value }) return index
                })
            }
        }
    }

    private fun clear() {
        modules.forEach { (name, module) ->
            module.output = false
            module.outputs.forEach { modules[it]?.run { inputs[name] = false } }
        }
    }

    private data class Pulse(val source: String, val destination: String, val value: Boolean)

    private data class Module(val type: Char, val name: String, val outputs: List<String>) {
        val inputs = mutableMapOf<String, Boolean>()
        var output: Boolean = false
        operator fun invoke(input: Pulse) = when (type) {
            '%' -> if (input.value) emptyList() else {
                output = !output
                outputs.map { Pulse(name, it, output) }
            }

            '&' -> {
                inputs[input.source] = input.value
                output = inputs.values.any { !it }
                outputs.map { Pulse(name, it, output) }
            }

            else -> error("unexpected type")
        }
    }

    private val broadcaster = lines.single { it.startsWith("broadcaster") }
        .removePrefix("broadcaster -> ").split(", ").map { Pulse("broadcaster", it, false) }
    private val modules = lines.filterNot { it.startsWith("broadcaster") }
        .associate { line ->
            val (input, output) = line.split(" -> ")
            input.drop(1) to Module(input[0], input.drop(1), output.split(", "))
        }
}
