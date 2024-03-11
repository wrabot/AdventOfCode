package aoc2017

import Day

class Day9(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        val s = input.remove(cancelRegex).remove(garbageRegex).remove("[^{}]".toRegex())
        var score = 0
        var level = 0
        s.forEach { if (it == '{') level++ else score += level-- }
        return score
    }

    override fun solvePart2() = garbageRegex.findAll(input.remove(cancelRegex)).sumOf { it.value.count() - 2 }

    private val cancelRegex = "!.".toRegex()
    private val garbageRegex = "<.*?>".toRegex()
    private fun String.remove(regex: Regex) = replace(regex, "")
}
