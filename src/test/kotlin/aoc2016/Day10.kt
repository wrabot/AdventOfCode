package aoc2016

import Day
import tools.text.match
import tools.text.toWords

class Day10(test: Int? = null) : Day(test) {
    override fun solvePart1() = botPart1
    override fun solvePart2() = part2

    private val inputRegex = "value (\\d+) goes to (.*)".toRegex()
    private val ruleRegex = "(.*) gives low to (.*) and high to (.*)".toRegex()
    private val microchips = mutableMapOf<String, MutableSet<Int>>()
    private val rules = mutableMapOf<String, Pair<String, String>>()
    private var botPart1 = ""
    private val part2: Int

    init {
        lines.map {
            it.match(inputRegex)?.let { (value, destination) ->
                microchips.getOrPut(destination) { mutableSetOf() }.add(value.toInt())
            } ?: it.match(ruleRegex)!!.let { (bot, low, high) ->
                rules[bot] = low to high
            }
        }
        val target = setOf(17, 61)
        while (true) {
            val current = microchips.filter { it.value.size == 2 }.firstNotNullOfOrNull { it } ?: break
            val rule = rules[current.key]!!
            microchips.getOrPut(rule.first) { mutableSetOf() }.add(current.value.min())
            microchips.getOrPut(rule.second) { mutableSetOf() }.add(current.value.max())
            if (current.value == target) botPart1 = current.key.toWords().last()
            current.value.clear()
        }
        part2 = microchips["output 0"]!!.first() * microchips["output 1"]!!.first() * microchips["output 2"]!!.first()
    }
}
