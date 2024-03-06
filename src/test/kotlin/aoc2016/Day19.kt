package aoc2016

import Day
import kotlin.math.log
import kotlin.math.pow

class Day19(test: Int? = null) : Day(test) {
    override fun solvePart1() = (numberOfElves - numberOfElves.closestPower(2.0)) * 2 + 1
    override fun solvePart2(): Int {
        val cp = (numberOfElves - 1).closestPower(3.0)
        val d = numberOfElves - cp
        return if (d <= cp) d else d * 2 - cp
    }

    private fun Int.closestPower(base: Double) = base.pow(log(toDouble(), base).toInt()).toInt()

    private val numberOfElves = input.toInt()

    // to analyse recurrence
    /*
    init {
        val part1 = false
        repeat(40) {
            val n = it + 1
            val elves = (1..n).toMutableList()
            var index = 0
            while (elves.size > 1) {
                val elf = elves[index]
                elves.removeAt((index + if (part1) 1 else elves.size / 2) % elves.size)
                index = (elves.indexOf(elf) + 1) % elves.size
            }
            println(n to elves.single())
        }
    }
     */
}